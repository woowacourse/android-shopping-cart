package woowacourse.shopping.data.cart

interface CartDataSource {
    fun insertCartItem(productId: Long)
    fun deleteCartItem(cartId: Long)
    fun updateCartItem(cartId: Long, quantity: Int)
    fun getCartItem(productId: Long): CartItem
    fun getAllCartItems(): List<CartItem>
}
