package woowacourse.shopping.presentation.ui.shoppingCart

import woowacourse.shopping.domain.PageNumber
import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ShoppingCartRepository

class ShoppingCartPresenter(
    private val view: ShoppingCartContract.View,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ShoppingCartContract.Presenter {
    private var shoppingCart = mutableListOf<ProductInCart>()
    private var pageNumber = PageNumber()

    override fun getShoppingCart() {
        shoppingCart.clear()
        shoppingCart.addAll(
            shoppingCartRepository.getShoppingCart(
                SHOPPING_CART_ITEM_COUNT,
                pageNumber.value,
            ),
        )
        view.setShoppingCart(shoppingCart.deepCopy())
    }

    override fun setPageNumber() {
        view.setPage(pageNumber.value)
    }

    private fun goOtherPage() {
        checkPageMovement()
        setPageNumber()
        getShoppingCart()
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

    override fun deleteProductInCart(index: Int) {
        val result = shoppingCartRepository.deleteProductInCart(shoppingCart[index].product.id)
        if (result) {
            shoppingCart.removeAt(index)
            view.setShoppingCart(shoppingCart.deepCopy())
        }
    }

    private fun MutableList<ProductInCart>.deepCopy(): List<ProductInCart> {
        return this.map { it.copy() }
    }

    companion object {
        private const val SHOPPING_CART_ITEM_COUNT = 5
    }
}
