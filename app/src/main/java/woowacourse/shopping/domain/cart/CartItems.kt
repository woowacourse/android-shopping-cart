package woowacourse.shopping.domain.cart

class CartItems(
    val value: List<CartItem> = emptyList(),
) {
    fun addCartItem(cartItem: CartItem): CartItems = CartItems(value + cartItem)

    fun removeCartItem(cartItem: CartItem): CartItems = CartItems(value.filter { !it.isSameCartItem(cartItem) })

    fun searchCartItem(cartItem: CartItem): Boolean = value.any { it.isSameCartItem(cartItem) }

    fun subList(fromIndex: Int, toIndex: Int): List<CartItem> {
        val safeFrom = fromIndex.coerceIn(0, value.size)
        val safeTo = toIndex.coerceIn(safeFrom, value.size)
        return value.subList(safeFrom, safeTo)
    }

    fun size(): Int = value.size

}
