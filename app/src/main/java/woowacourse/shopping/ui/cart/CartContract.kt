package woowacourse.shopping.ui.cart

import woowacourse.shopping.ui.cart.uistate.CartUIState

interface CartContract {
    interface Presenter {
        fun loadCartItems()
        fun loadCartItems(limit: Int, offset: Int)
        fun deleteCartItem(productId: Long)
        fun setPageButtons(limit: Int)
    }

    interface View {
        fun setCartItems(cartItems: List<CartUIState>)
        fun setButtonClickListener(maxOffset: Int)
    }
}
