package woowacourse.shopping.domain.cart

class CartItems(
    private val value: List<CartItem>,
) {
    fun addCartItem(cartItem: CartItem): CartItems = CartItems(value + cartItem)

    fun removeCartItem(cartItem: CartItem): CartItems = CartItems(value.filter { !it.isSameCartItem(cartItem) })

    fun searchCartItem(cartItem: CartItem): Boolean = value.any { it.isSameCartItem(cartItem) }
}
