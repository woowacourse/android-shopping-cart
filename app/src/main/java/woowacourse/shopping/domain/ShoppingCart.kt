package woowacourse.shopping.domain

class ShoppingCart() {
    private val _cartItems: MutableList<CartItem> = mutableListOf()
    val cartItems: List<CartItem> get() = _cartItems

    fun addProduct(cartItem: CartItem) {
        _cartItems.add(cartItem)
    }

    fun deleteProduct(itemId: Long) {
        _cartItems.removeIf { it.id == itemId }
    }
}
