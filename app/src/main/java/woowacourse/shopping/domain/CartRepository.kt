package woowacourse.shopping.domain

interface CartRepository {
    fun getCartItems(onResult: (Result<List<CartItem>>) -> Unit)

    fun addToCart(item: CartItem)

    fun removeFromCart(
        productId: Long,
        onResult: (Result<Long>) -> Unit,
    )

    fun increaseQuantity(productId: Long): Result<Unit>

    fun decreaseQuantity(productId: Long): Result<Unit>
}
