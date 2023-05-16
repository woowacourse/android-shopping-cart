package woowacourse.shopping.ui.cart

import woowacourse.shopping.ui.cart.uistate.CartItemUIState

interface CartContract {
    interface Presenter {
        fun onLoadCartItemsNextPage()
        fun onLoadCartItemsPreviousPage()
        fun onDeleteCartItem(productId: Long)
    }

    interface View {
        fun setCartItems(cartItems: List<CartItemUIState>)
        fun setStateThatCanRequestPreviousPage(canRequest: Boolean)
        fun setStateThatCanRequestNextPage(canRequest: Boolean)
        fun setPage(page: Int)
    }
}
