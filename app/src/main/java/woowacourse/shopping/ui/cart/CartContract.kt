package woowacourse.shopping.ui.cart

import woowacourse.shopping.ui.cart.uistate.CartItemUIState

interface CartContract {
    interface Presenter {
        fun getCurrentPage(): Int
        fun restoreCurrentPage(currentPage: Int)
        fun onLoadCartItemsNextPage()
        fun onLoadCartItemsPreviousPage()
        fun onLoadCartItemsLastPage()
        fun onDeleteCartItem(productId: Long)
        fun onChangeCartItemSelection(productId: Long, isSelected: Boolean)
    }

    interface View {
        fun setCartItems(cartItems: List<CartItemUIState>)
        fun setStateThatCanRequestPreviousPage(canRequest: Boolean)
        fun setStateThatCanRequestNextPage(canRequest: Boolean)
        fun setPage(page: Int)
        fun setCartItemSelected(productId: Long, isSelected: Boolean)
        fun setOrderPrice(price: Int)
        fun setOrderCount(count: Int)
    }
}
