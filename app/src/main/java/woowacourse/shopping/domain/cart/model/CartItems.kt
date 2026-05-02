package woowacourse.shopping.domain.cart.model

class CartItems(
    private val value: List<CartItem> = emptyList(),
) {
    fun addCartItem(cartItem: CartItem): CartItems = CartItems(value + cartItem)

    fun removeCartItem(cartItem: CartItem): CartItems = CartItems(value.filter { !it.isSameCartItem(cartItem) })

    fun searchCartItem(cartItem: CartItem): Boolean = value.any { it.isSameCartItem(cartItem) }

    fun subList(
        fromIndex: Int,
        toIndex: Int,
    ): List<CartItem> = value.subList(fromIndex, minOf(toIndex, value.size))

    fun size(): Int = value.size
}
