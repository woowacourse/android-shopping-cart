package woowacourse.shopping.repository

import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductId
import woowacourse.shopping.model.Products

interface ProductRepository {
    val size: Int

    suspend fun getProducts(
        fromIndex: Int,
        limit: Int,
    ): Products

    suspend fun hasNext(current: Int): Boolean

    suspend fun findAllByIds(ids: Set<ProductId>): Map<ProductId, Product>
}
