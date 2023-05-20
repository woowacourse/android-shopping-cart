package woowacourse.shopping.cart

import com.shopping.domain.Cart
import com.shopping.domain.PageNumber
import woowacourse.shopping.datas.CartRepository
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.uimodel.CartProductUIModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository
) : CartContract.Presenter {
    private var pageNumber = PageNumber()
    private var cart = Cart(emptyList())

    override fun removeProduct(cartProductUIModel: CartProductUIModel, position: Int) {
        cartRepository.remove(cartProductUIModel.product.id)
        view.removeAdapterData(cartProductUIModel, position)
    }

    override fun getCartProducts() {
        val cartProducts = cartRepository.getUnitData(CART_UNIT_SIZE, pageNumber.value)
        cart = Cart(cart.products + cartProducts)
        view.setCartProducts(cartProducts.map { it.toUIModel() })
    }

    override fun goNextPage() {
        if (cartRepository.getSize() + CART_UNIT_SIZE <= CART_UNIT_SIZE * (pageNumber.value + 1)) return
        pageNumber = pageNumber.nextPage()
        setPageNumber()
        changePage(pageNumber.value)
    }

    override fun goPreviousPage() {
        pageNumber = pageNumber.previousPage()
        setPageNumber()
        changePage(pageNumber.value)
    }

    override fun setPageNumber() {
        view.showPageNumber(pageNumber.value)
    }

    override fun changePage(page: Int) {
        val size = cartRepository.getSize()
        val unitSize =
            if (size / CART_UNIT_SIZE < page) size - (CART_UNIT_SIZE * page) else CART_UNIT_SIZE
        view.setCartProducts(cartRepository.getUnitData(unitSize, page).map { it.toUIModel() })
    }

    override fun updateProductIsPicked(product: CartProductUIModel, isPicked: Boolean) {
        cartRepository.updateProductIsPicked(product.product.id, isPicked)
    }

    companion object {
        private const val CART_UNIT_SIZE = 5
    }
}
