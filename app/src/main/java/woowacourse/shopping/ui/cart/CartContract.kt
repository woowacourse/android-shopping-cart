package woowacourse.shopping.ui.cart

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
