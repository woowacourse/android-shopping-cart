package woowacourse.shopping.domain.repository

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.domain.CartItems

interface CartRepository {
    suspend fun addItem(
        productId: String,
        amount: Int,
    )

    suspend fun deleteItem(productId: String)

    suspend fun minusItemAmount(productId: String)

    suspend fun getCartItemByPage(page: Int, pageSize: Int): CartItems

    fun getAllCartItems(): Flow<CartItems>
}
