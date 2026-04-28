package woowacourse.shopping.domain
class Cart(private val cartItems: List<CartItem> = emptyList()) {
    fun plusCartItem(cartItem: CartItem): Cart = Cart(cartItems + cartItem)

    fun isContains(product: Product): Boolean = cartItems.any { it.isSame(product) }
}
