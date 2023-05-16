package woowacourse.shopping.presentation.view.cart

import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.data.respository.cart.CartRepository

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository
) : CartContract.Presenter {
    override fun loadCartItems(currentPage: Int) {
        val startPosition = getStartItemPosition(currentPage)
        var newCarts = cartRepository.getCarts(startPosition).map { it.toUIModel() }
        view.setEnableLeftButton(currentPage != FIRST_PAGE_NUMBER)
        view.setEnableRightButton(newCarts.size > DISPLAY_CART_COUNT_CONDITION)

        val subToIndex = if (newCarts.size > DISPLAY_CART_COUNT_CONDITION) newCarts.lastIndex else newCarts.size
        newCarts = newCarts.subList(CART_LIST_FIRST_INDEX, subToIndex)
        view.setCartItemsView(newCarts)
    }

    override fun deleteCartItem(currentPage: Int, itemId: Long) {
        cartRepository.deleteCartByProductId(itemId)
        loadCartItems(currentPage)
    }

    private fun getStartItemPosition(currentPage: Int): Int =
        (currentPage - 1) * DISPLAY_CART_COUNT_CONDITION

    companion object {
        private const val FIRST_PAGE_NUMBER = 1
        private const val DISPLAY_CART_COUNT_CONDITION = 3
        private const val CART_LIST_FIRST_INDEX = 0
    }
}
