package woowacourse.shopping.domain.cart.model

class CartItems(
    _value: List<CartItem> = emptyList(),
) {
    private val value: List<CartItem> = _value.toList()

    fun addCartItem(cartItem: CartItem, targetQuantity: Int): CartItems {
        return if (!searchCartItem(cartItem)) {
            CartItems(_value = value + cartItem.increaseQuantity(targetQuantity))
        } else {
            CartItems(_value = value.map {
                if (it.isSameCartItem(cartItem)) {
                    it.increaseQuantity(targetQuantity)
                } else {
                    it
                }
            })
        }
    }

    fun minusCartItem(cartItem: CartItem, targetQuantity: Int): CartItems = CartItems(_value = value.map {
        if (it.isSameCartItem(cartItem)) {
            it.decreaseQuantity(targetQuantity)
        } else {
            it
        }
    })

    fun removeCartItem(cartItem: CartItem): CartItems = CartItems(_value = value.filter { !it.isSameCartItem(cartItem) })

    fun searchCartItem(cartItem: CartItem): Boolean = value.any { it.isSameCartItem(cartItem) }

    fun getTotalCartItemCount(): Int = value.sumOf { it.quantity.value }

    fun subList(
        fromIndex: Int,
        toIndex: Int,
    ): List<CartItem> = value.subList(fromIndex, minOf(toIndex, value.size))

    fun size(): Int = value.size
}
