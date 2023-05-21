package woowacourse.shopping.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shopping.domain.Cart
import com.shopping.domain.PageNumber
import woowacourse.shopping.datas.CartRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.uimodel.CartProductUIModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository
) : CartContract.Presenter {
    private var pageNumber = PageNumber()
    private var cart = Cart()
    private var recentPageProducts = Cart()

    private val _totalPrice: MutableLiveData<Int> = MutableLiveData(0)
    val totalPrice: LiveData<Int> get() = _totalPrice
    private val _totalPickedProductsCount: MutableLiveData<Int> = MutableLiveData(0)
    val totalPickedProductsCount: LiveData<Int> get() = _totalPickedProductsCount

    override fun getCartProducts() {
        val cartProducts = cartRepository.getUnitData(CART_UNIT_SIZE, pageNumber.value)
        cart = cart.addAll(cartProducts)
        recentPageProducts = Cart(cartProducts)
        calculateTotalPrice()
        view.setCartProducts(cartProducts.map { it.toUIModel() })
        updateAllChecked()
        updateCountOfProductType()
    }

    override fun removeProduct(cartProductUIModel: CartProductUIModel, position: Int) {
        cartRepository.remove(cartProductUIModel.product.id)
        cart = cart.remove(cartProductUIModel.toDomain())
        recentPageProducts = Cart(recentPageProducts.products - cartProductUIModel.toDomain())
        view.removeAdapterData(cartProductUIModel, position)
        updateAllChecked()
        updateCountOfProductType()
    }

    override fun goNextPage() {
        if (cartRepository.getSize() + CART_UNIT_SIZE <= CART_UNIT_SIZE * (pageNumber.value + 1)) return
        pageNumber = pageNumber.nextPage()
        setPageNumber()
        changePage(pageNumber.value)
        updateAllChecked()
    }

    override fun goPreviousPage() {
        pageNumber = pageNumber.previousPage()
        setPageNumber()
        changePage(pageNumber.value)
        updateAllChecked()
    }

    override fun setPageNumber() {
        view.showPageNumber(pageNumber.value)
    }

    override fun changePage(page: Int) {
        val size = cartRepository.getSize()
        val unitSize =
            if (size / CART_UNIT_SIZE < page) size - (CART_UNIT_SIZE * page) else CART_UNIT_SIZE
        val cartProducts = cartRepository.getUnitData(unitSize, page)
        recentPageProducts = Cart(cartProducts)
        view.setCartProducts(cartProducts.map { it.toUIModel() })
    }

    override fun updateProductIsPicked(product: CartProductUIModel, isPicked: Boolean) {
        cartRepository.updateProductIsPicked(product.product.id, isPicked)
        cart = cart.updateIsPicked(product.toDomain(), isPicked)
        recentPageProducts = recentPageProducts.updateIsPicked(product.toDomain(), isPicked)
        calculateTotalPrice()
        updateAllChecked()
        updateCountOfProductType()
    }

    override fun calculateTotalPrice() {
        _totalPrice.value = cart.getPickedProductsTotalPrice()
    }

    override fun updateIsPickAllProduct(isPicked: Boolean) {
        cart = cart.removeAll(recentPageProducts)
        recentPageProducts = recentPageProducts.setIsPickAllProduct(isPicked)
        recentPageProducts.products.forEach {
            cartRepository.updateProductIsPicked(it.product.id, isPicked)
        }
        cart = cart.addAll(recentPageProducts)
        updateAllChecked()
        updateCountOfProductType()
    }

    override fun updateAllChecked() {
        view.refreshAllChecked(recentPageProducts.isAllPicked())
    }

    override fun updateCountOfProductType() {
        _totalPickedProductsCount.value = cart.getTotalPickedProductsCount()
    }

    override fun updateCartProductCount(cartProduct: CartProductUIModel, count: Int) {
        if (count <= 0) return
        cart = cart.updateProductCount(cartProduct.toDomain(), count)
        cartRepository.updateProductCount(cartProduct.product.id, count)
    }

    companion object {
        private const val CART_UNIT_SIZE = 5
    }
}
