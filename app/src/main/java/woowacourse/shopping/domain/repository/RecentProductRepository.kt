package woowacourse.shopping.domain.repository

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.domain.model.product.Product

interface RecentProductRepository {
    fun getRecentProducts(): Flow<List<Product>>
    suspend fun saveRecentProduct(product: Product)
}
