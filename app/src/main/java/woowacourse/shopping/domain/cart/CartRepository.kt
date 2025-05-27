package woowacourse.shopping.domain.cart

interface CartRepository {
    fun fetchInRange(
        limit: Int,
        offset: Int,
        onResult: (Result<List<CartProduct>>) -> Unit,
    )

    fun fetchByProductId(
        productId: Long,
        onResult: (Result<CartProduct?>) -> Unit,
    )

    fun insert(
        productId: Long,
        quantity: Int,
        onResult: (Result<Long>) -> Unit,
    )

    fun insertOrAddQuantity(
        productId: Long,
        quantity: Int,
        onResult: (Result<Unit>) -> Unit,
    )

    fun updateQuantity(
        productId: Long,
        delta: Int,
        onResult: (Result<Unit>) -> Unit,
    )

    fun delete(
        cartItemId: Long,
        onResult: (Result<Unit>) -> Unit,
    )
}
