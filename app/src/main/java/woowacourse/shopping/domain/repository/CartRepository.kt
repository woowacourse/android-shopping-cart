package woowacourse.shopping.domain.repository

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product

interface CartRepository {
    suspend fun addItem(
        productId: String,
        amount: Int,
    )

    suspend fun deleteItem(productId: String)

    suspend fun getCartItemByPage(page: Int): List<CartItem>

    suspend fun isLastPage(page: Int): Boolean

    suspend fun getItemCount(productId: String): Int

    suspend fun plusItemCount(product: Product)

    suspend fun minusItemCount(productId: String)

    fun getCartItemCount(): Flow<Int>
}
