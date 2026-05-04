package woowacourse.shopping.repository

import woowacourse.shopping.model.ProductId

interface CartRepository {
    suspend fun add(item: ProductId)

    suspend fun delete(item: ProductId)

    suspend fun getCartItems(
        fromIndex: Int,
        limit: Int,
    ): Map<ProductId, Int>

    suspend fun count(): Int
}
