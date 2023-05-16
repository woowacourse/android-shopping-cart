package woowacourse.shopping.presentation.view.cart

import woowacourse.shopping.data.respository.cart.CartRepository

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository
) : CartContract.Presenter {
    override fun loadCartItems(currentPage: String) {
        val currentPageNumber = currentPage.toInt()
        val startPosition = getStartItemPosition(currentPageNumber)
        var newCarts = cartRepository.getCarts(startPosition, GET_CART_ITEM_COUNT)
        view.setEnableLeftButton(currentPageNumber != FIRST_PAGE_NUMBER)
        view.setEnableRightButton(newCarts.size > DISPLAY_CART_COUNT_CONDITION)

        val subToIndex =
            if (newCarts.size > DISPLAY_CART_COUNT_CONDITION) newCarts.lastIndex else newCarts.size
        newCarts = newCarts.subList(CART_LIST_FIRST_INDEX, subToIndex)
        view.setCartItemsView(newCarts, currentPage)
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
