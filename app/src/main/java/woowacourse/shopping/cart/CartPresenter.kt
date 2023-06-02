package woowacourse.shopping.cart

import com.shopping.domain.Cart
import com.shopping.domain.CartRepository
import com.shopping.domain.PageNumber
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.uimodel.CartProductUIModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository
) : CartContract.Presenter {
    private var pageNumber = PageNumber()
    private var cart = Cart()
    private var currentPageProducts = Cart()

    override fun fetchCartProducts() {
        val cartProducts = cartRepository.getUnitData(CART_UNIT_SIZE, pageNumber.value)
        cart = cart.addAll(cartProducts)
        currentPageProducts = Cart(cartProducts)
        view.setTotalPrice(cart.getPickedProductsTotalPrice())
        view.setCartProducts(cartProducts.map { it.toUIModel() })
        view.setAllChecked(currentPageProducts.isAllPicked())
        view.setOrderProductTypeCount(cart.getTotalPickedProductsCount())
    }

    override fun removeProduct(cartProductUIModel: CartProductUIModel) {
        cartRepository.remove(cartProductUIModel.product.id)
        cart = cart.remove(cartProductUIModel.toDomain())
        view.removeAdapterData(
            cartProductUIModel,
            currentPageProducts.products.indexOf(cartProductUIModel.toDomain())
        )
        currentPageProducts = currentPageProducts.remove(cartProductUIModel.toDomain())
        view.setAllChecked(currentPageProducts.isAllPicked())
        view.setOrderProductTypeCount(cart.getTotalPickedProductsCount())
    }

    override fun goNextPage() {
        if (cartRepository.getSize() + CART_UNIT_SIZE <= CART_UNIT_SIZE * (pageNumber.value + 1)) return
        pageNumber = pageNumber.nextPage()
        updatePageNumber()
        changePage(pageNumber.value)
        view.setAllChecked(currentPageProducts.isAllPicked())
    }

    override fun goPreviousPage() {
        pageNumber = pageNumber.previousPage()
        updatePageNumber()
        changePage(pageNumber.value)
        view.setAllChecked(currentPageProducts.isAllPicked())
    }

    override fun updatePageNumber() {
        view.showPageNumber(pageNumber.value)
    }

    private fun changePage(page: Int) {
        val size = cartRepository.getSize()
        val unitSize =
            if (size / CART_UNIT_SIZE < page) size - (CART_UNIT_SIZE * page) else CART_UNIT_SIZE
        val cartProducts = cartRepository.getUnitData(unitSize, page)
        currentPageProducts = Cart(cartProducts)
        view.setCartProducts(cartProducts.map { it.toUIModel() })
    }

    override fun updateProductIsPicked(product: CartProductUIModel, isPicked: Boolean) {
        cartRepository.updateProductIsPicked(product.product.id, isPicked)
        cart = cart.updateIsPicked(product.toDomain(), isPicked)
        currentPageProducts = currentPageProducts.updateIsPicked(product.toDomain(), isPicked)
        view.setTotalPrice(cart.getPickedProductsTotalPrice())
        view.setAllChecked(currentPageProducts.isAllPicked())
        view.setOrderProductTypeCount(cart.getTotalPickedProductsCount())
    }

    override fun updateIsPickAllProduct(isPicked: Boolean) {
        cart = cart.removeAll(currentPageProducts)
        currentPageProducts = currentPageProducts.setIsPickAllProduct(isPicked)
        currentPageProducts.products.forEach {
            cartRepository.updateProductIsPicked(it.product.id, isPicked)
        }
        cart = cart.addAll(currentPageProducts)
        view.setAllChecked(currentPageProducts.isAllPicked())
        view.setOrderProductTypeCount(cart.getTotalPickedProductsCount())
        view.setTotalPrice(cart.getPickedProductsTotalPrice())
    }

    override fun updateCartProductCount(cartProduct: CartProductUIModel, count: Int) {
        if (count <= 0) return
        cart = cart.updateProductCount(cartProduct.toDomain(), count)
        currentPageProducts = currentPageProducts.updateProductCount(cartProduct.toDomain(), count)
        cartRepository.updateProductCount(cartProduct.product.id, count)
        view.setTotalPrice(cart.getPickedProductsTotalPrice())
    }

    companion object {
        private const val CART_UNIT_SIZE = 5
    }
}
