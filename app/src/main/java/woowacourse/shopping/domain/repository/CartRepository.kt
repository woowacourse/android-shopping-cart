package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.AddItemResult
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Product

interface CartRepository {
    suspend fun getCart(): Cart

    suspend fun getTotalCartSize(): Int

    suspend fun addItem(product: Product): AddItemResult

    suspend fun deleteItem(id: String)
}
