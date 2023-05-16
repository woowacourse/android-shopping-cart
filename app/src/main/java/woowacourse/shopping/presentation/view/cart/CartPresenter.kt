package woowacourse.shopping.presentation.view.cart

import woowacourse.shopping.data.respository.cart.CartRepository
import woowacourse.shopping.presentation.model.CartModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository
) : CartContract.Presenter {
    override fun loadCartItems(currentPage: String) {
        val currentPageNumber = currentPage.toInt()
        var newCarts = getNewCarts(currentPageNumber)
        view.setEnableLeftButton(currentPageNumber != FIRST_PAGE_NUMBER)
        view.setEnableRightButton(newCarts.size > DISPLAY_CART_COUNT_CONDITION)

        newCarts = submitNewCarts(newCarts)
        view.setCartItemsView(newCarts, currentPage)
    }

    private fun submitNewCarts(newCarts: List<CartModel>): List<CartModel> {
        var newCarts1 = newCarts
        val subToIndex =
            if (newCarts1.size > DISPLAY_CART_COUNT_CONDITION) newCarts1.lastIndex else newCarts1.size
        newCarts1 = newCarts1.subList(CART_LIST_FIRST_INDEX, subToIndex)
        return newCarts1
    }

    private fun getNewCarts(currentPageNumber: Int): List<CartModel> {
        val startPosition = getStartItemPosition(currentPageNumber)
        return cartRepository.getCarts(startPosition, GET_CART_ITEM_COUNT)
    }

    override fun deleteCartItem(currentPage: String, itemId: Long) {
        cartRepository.deleteCartByProductId(itemId)
        loadCartItems(currentPage)
    }

    private fun getStartItemPosition(currentPage: Int): Int =
        (currentPage - 1) * DISPLAY_CART_COUNT_CONDITION

    override fun decrementPage(currentPage: String) {
        loadCartItems(currentPage.toInt().dec().toString())
    }

    override fun incrementPage(currentPage: String) {
        loadCartItems(currentPage.toInt().inc().toString())
    }

    companion object {
        private const val FIRST_PAGE_NUMBER = 1
        private const val DISPLAY_CART_COUNT_CONDITION = 3
        private const val CART_LIST_FIRST_INDEX = 0
        private const val GET_CART_ITEM_COUNT = 4
    }
}
