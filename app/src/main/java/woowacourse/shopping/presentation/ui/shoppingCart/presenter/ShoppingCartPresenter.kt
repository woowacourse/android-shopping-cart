package woowacourse.shopping.presentation.ui.shoppingCart.presenter

import woowacourse.shopping.domain.PageNumber
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ShoppingCartRepository

class ShoppingCartPresenter(
    private val view: ShoppingCartContract.View,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ShoppingCartContract.Presenter {
    lateinit var shoppingCart: List<ProductInCart>
    private var pageNumber = PageNumber()

    override fun getShoppingCart(page: Int) {
        val shoppingCart = shoppingCartRepository.getShoppingCartByPage(SHOPPING_CART_ITEM_COUNT, page)
        view.setShoppingCart(shoppingCart)
    }

    override fun setPageNumber() {
        view.setPage(pageNumber.value)
    }

    private fun getPage() {
        val shoppingCart =
            shoppingCartRepository.getShoppingCartByPage(SHOPPING_CART_ITEM_COUNT, pageNumber.value)
        view.setShoppingCart(shoppingCart)
    }

    private fun goOtherPage() {
        checkPageMovement()
        setPageNumber()
        getPage()
    }

    override fun goNextPage() {
        pageNumber = pageNumber.nextPage()
        goOtherPage()
    }

    override fun goPreviousPage() {
        pageNumber = pageNumber.previousPage()
        goOtherPage()
    }

    override fun checkPageMovement() {
        val size = shoppingCartRepository.getShoppingCartSize()
        val nextEnable = size > pageNumber.value * SHOPPING_CART_ITEM_COUNT
        val previousEnable = pageNumber.value > 1
        view.setPageButtonEnable(previousEnable, nextEnable)
    }

    override fun deleteProductInCart(productId: Long): Boolean {
        return shoppingCartRepository.deleteProductInCart(productId)
    }

    companion object {
        private const val SHOPPING_CART_ITEM_COUNT = 5
    }
}
