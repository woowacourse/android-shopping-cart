package woowacourse.shopping.domain.cart

interface CartRepository {
    fun fetchInRange(
        limit: Int,
        offset: Int,
        onResult: (List<CartProduct>) -> Unit,
    )

    fun fetchByProductId(
        productId: Long,
        onResult: (CartProduct) -> Unit,
    )

    fun insert(productId: Long, quantity: Int, onResult: (Result<Long>) -> Unit)

    fun insertOrAddQuantity(productId: Long, quantity: Int, onResult: (Result<Unit>) -> Unit)

    fun delete(
        cartItemId: Long,
        onResult: (Unit) -> Unit,
    )
}
