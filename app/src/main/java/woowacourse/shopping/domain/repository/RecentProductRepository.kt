package woowacourse.shopping.domain.repository

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.domain.product.Product

interface RecentProductRepository {
    fun getRecentProducts(limit: Int = DEFAULT_LIMIT): Flow<List<Product>>

    suspend fun getMostRecentProduct(): Product?

    suspend fun save(product: Product)

    companion object {
        const val DEFAULT_LIMIT: Int = 10
    }
}
