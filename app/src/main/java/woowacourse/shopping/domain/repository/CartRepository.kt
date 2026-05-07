package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.AddItemResult
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.RemoveItemResult

interface CartRepository {
    suspend fun getCart(): Cart

    suspend fun getTotalCartSize(): Int

    suspend fun addItem(
        id: String,
        quantity: Int = 1,
    ): AddItemResult

    suspend fun deleteItem(id: String): RemoveItemResult

    suspend fun decrease(id: String): RemoveItemResult

    suspend fun getAllQuantities(): Map<String, Int>

    suspend fun getQuantity(id: String): Int
}
