package woowacourse.shopping.ui.cart

import woowacourse.shopping.ui.cart.uistate.CartUIState

interface CartContract {
    interface Presenter {
        fun loadCartItems()
        fun deleteCartItem(productId: Long)
    }

    interface View {
        fun setCartItems(cartItems: List<CartUIState>)
    }
}
