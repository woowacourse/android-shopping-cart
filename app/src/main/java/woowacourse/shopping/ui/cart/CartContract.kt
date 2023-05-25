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

    interface View {
        fun setStateThatCanRequestPreviousPage(canRequest: Boolean)

        fun setStateThatCanRequestNextPage(canRequest: Boolean)

        fun setPage(page: Int)

        fun setCartItems(cartItems: List<CartItemUIState>, initScroll: Boolean)

        fun setStateOfAllSelection(isAllSelected: Boolean)

        fun setOrderPrice(price: Int)

        fun setOrderCount(count: Int)
    }
}
