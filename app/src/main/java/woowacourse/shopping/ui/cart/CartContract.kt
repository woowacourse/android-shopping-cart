package woowacourse.shopping.ui.cart

import woowacourse.shopping.ui.cart.uistate.CartUIState

interface CartContract {
    interface Presenter {
        fun loadCartItems()
        fun loadCartItems(limit: Int, page: Int)
        fun deleteCartItem(productId: Long)
        fun setPageButtons(limit: Int)
        fun minusItemCount(productId: Long, oldCount: Int)
        fun plusItemCount(productId: Long, oldCount: Int)
    }

    interface View {
        fun setCartItems(cartItems: List<CartUIState>)
        fun setPageButtonClickListener(maxOffset: Int)
        fun updateCartItems(cartItems: List<CartUIState>)
    }
}
