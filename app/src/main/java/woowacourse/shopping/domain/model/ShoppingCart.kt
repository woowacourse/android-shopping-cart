package woowacourse.shopping.domain.model

class ShoppingCart {
    private val _cartItems: MutableList<CartItem> = mutableListOf()
    val cartItems: List<CartItem> get() = _cartItems

    fun addProduct(cartItem: CartItem) {
        _cartItems.add(cartItem)
    }

    fun deleteProduct(itemId: Long) {
        _cartItems.removeIf { it.id == itemId }
    }

    companion object {
        fun makeShoppingCart(cartItems: List<CartItem>): ShoppingCart{
            return ShoppingCart().apply {
                _cartItems.addAll(cartItems.toMutableList())
            }
        }
    }
}
