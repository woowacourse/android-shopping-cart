package woowacourse.shopping.domain.cart.model

class CartItems(
    _value: List<CartItem> = emptyList(),
) {
    private val value: List<CartItem> = _value.toList()

    fun addCartItem(cartItem: CartItem): CartItems = CartItems(value + cartItem)

    fun removeCartItem(cartItem: CartItem): CartItems = CartItems(value.filter { !it.isSameCartItem(cartItem) })

    fun searchCartItem(cartItem: CartItem): Boolean = value.any { it.isSameCartItem(cartItem) }

    fun increaseCartItem(cartItem: CartItem): CartItems = CartItems(value.map { if (it.isSameCartItem(cartItem)) it.increaseQuantity(cartItem) else it })

    fun decreaseCartItem(cartItem: CartItem): CartItems = CartItems(value.map { if (it.isSameCartItem(cartItem)) it.decreaseQuantity(cartItem) else it })

    fun getTotalCartItemCount(): Int = value.sumOf { it.quantity.value }

    fun subList(
        fromIndex: Int,
        toIndex: Int,
    ): List<CartItem> = value.subList(fromIndex, minOf(toIndex, value.size))

    fun size(): Int = value.size
}
