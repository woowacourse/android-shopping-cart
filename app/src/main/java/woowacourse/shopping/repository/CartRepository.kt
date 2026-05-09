package woowacourse.shopping.repository

import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product

interface CartRepository {
    suspend fun increase(item: Product)

    suspend fun decrease(item: Product)

    suspend fun delete(item: Product)

    suspend fun getPagedItems(
        fromIndex: Int,
        count: Int,
    ): List<CartItem>

    suspend fun getSize(): Int
}
