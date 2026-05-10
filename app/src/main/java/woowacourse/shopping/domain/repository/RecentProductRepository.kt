package woowacourse.shopping.domain.repository

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.domain.Product

interface RecentProductRepository {
    fun getRecentProducts(): Flow<List<Product>>

    suspend fun addRecentProduct(product: Product)
}
