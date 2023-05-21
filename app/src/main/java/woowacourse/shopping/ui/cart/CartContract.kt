package woowacourse.shopping.ui.cart

import woowacourse.shopping.ui.cart.uistate.CartItemUIState

interface CartContract {

    interface Presenter {
        val currentPage: Int

        val selectedCartItemIds: List<Long>

        fun restoreCurrentPage(currentPage: Int)

        fun restoreSelectedCartItems(cartItemIds: List<Long>)

        fun onLoadCartItemsOfNextPage()

        fun onLoadCartItemsOfPreviousPage()

        fun onLoadCartItemsOfLastPage()

        fun onDeleteCartItem(cartItemId: Long)

        fun onChangeSelectionOfCartItem(cartItemId: Long, isSelected: Boolean)

        fun onChangeSelectionOfAllCartItems(isSelected: Boolean)

        fun onPlusCount(cartItemId: Long)

        fun onMinusCount(cartItemId: Long)
    }

    interface View : PageableView, CartItemsView, AllSelectableItemView, OrderView
}

interface PageableView {
    fun setStateThatCanRequestPreviousPage(canRequest: Boolean)

    fun setStateThatCanRequestNextPage(canRequest: Boolean)

    fun setPage(page: Int)
}

interface CartItemsView {
    fun setCartItems(cartItems: List<CartItemUIState>, initScroll: Boolean)
}

interface AllSelectableItemView {
    fun setStateOfAllSelection(isAllSelected: Boolean)
}

interface OrderView {
    fun setOrderPrice(price: Int)

    fun setOrderCount(count: Int)
}