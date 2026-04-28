package woowacourse.shopping.domain.cart

data class Cart(
    val cartItems: CartItems,
) {
    fun addCartItem(cartItem: CartItem): Cart = copy(cartItems = cartItems.addCartItem(cartItem))

    fun removeCartItem(cartItem: CartItem): Cart = copy(cartItems = cartItems.removeCartItem(cartItem))
}
