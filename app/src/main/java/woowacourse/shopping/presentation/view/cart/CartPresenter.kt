package woowacourse.shopping.presentation.view.cart

import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.data.respository.cart.CartRepository

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private var currentPage: Int = 1
) : CartContract.Presenter {
    private val startPosition: Int
        get() = (currentPage - 1) * DISPLAY_CART_COUNT_CONDITION

    override fun loadCartItems() {
        var newCarts = cartRepository.getCarts(startPosition).map { it.toUIModel() }
        view.setEnableLeftButton(currentPage != FIRST_PAGE_NUMBER)
        view.setEnableRightButton(newCarts.size > DISPLAY_CART_COUNT_CONDITION)

        val subToIndex = if (newCarts.size > DISPLAY_CART_COUNT_CONDITION) newCarts.lastIndex else newCarts.size
        newCarts = newCarts.subList(CART_LIST_FIRST_INDEX, subToIndex)
        view.setCartItemsView(newCarts)
    }

    override fun deleteCartItem(itemId: Long) {
        cartRepository.deleteCartByProductId(itemId)
        loadCartItems()
    }

    override fun calculatePreviousPage() {
        view.setPageCountView(--currentPage)
    }

    override fun calculateNextPage() {
        view.setPageCountView(++currentPage)
    }

    companion object {
        private const val FIRST_PAGE_NUMBER = 1
        private const val DISPLAY_CART_COUNT_CONDITION = 3
        private const val CART_LIST_FIRST_INDEX = 0
    }
}
