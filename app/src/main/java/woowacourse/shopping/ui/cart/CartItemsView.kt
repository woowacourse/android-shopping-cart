package woowacourse.shopping.ui.cart

import woowacourse.shopping.ui.cart.uistate.CartItemUIState

interface CartItemsView {
    fun setCartItems(cartItems: List<CartItemUIState>, initScroll: Boolean)
}