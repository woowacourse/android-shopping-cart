package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.AddItemResult
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.RemoveItemResult

interface CartRepository {
    suspend fun getCart(): Cart

    suspend fun getTotalCartSize(): Int

    suspend fun addItem(
        id: Long,
        quantity: Int = 1,
    ): AddItemResult

    suspend fun deleteItem(id: Long): RemoveItemResult

    suspend fun decrease(id: Long): RemoveItemResult

    suspend fun getAllQuantities(): Map<Long, Int>

    suspend fun getQuantity(id: Long): Int
}
