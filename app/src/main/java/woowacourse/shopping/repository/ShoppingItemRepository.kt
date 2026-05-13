package woowacourse.shopping.repository

import kotlinx.coroutines.flow.StateFlow
import woowacourse.shopping.model.ShoppingItem

interface ShoppingItemRepository {
    val shoppingItems: StateFlow<List<ShoppingItem>>

    suspend fun getQuantity(productId: Long): Int

    suspend fun plusQuantity(
        productId: Long,
        amount: Int = 1,
    )

    suspend fun minusQuantity(
        productId: Long,
        amount: Int = 1,
    )
}
