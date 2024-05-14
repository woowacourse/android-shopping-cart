package woowacourse.shopping.domain.model

class ShoppingCart {
    private val _cartItems: MutableList<CartItem> = mutableListOf()
    val cartItems: List<CartItem> get() = _cartItems

    fun initializeProducts(cartItems: List<CartItem>){
        _cartItems.clear()
        _cartItems.addAll(cartItems.toMutableList())
    }

    fun addProduct(cartItem: CartItem) {
        _cartItems.add(cartItem)
    }

    fun deleteProduct(itemId: Long) {
        _cartItems.removeIf { it.id == itemId }
    }
}
