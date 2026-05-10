package woowacourse.shopping.domain.repository

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Quantity

interface CartRepository {
    fun getCart(): Flow<List<CartItem>>

    suspend fun addCartItem(
        product: Product,
        quantity: Quantity,
    )

    suspend fun decreaseCartItem(
        product: Product,
        quantity: Quantity,
    )

    suspend fun deleteCartItem(productId: String)
}
