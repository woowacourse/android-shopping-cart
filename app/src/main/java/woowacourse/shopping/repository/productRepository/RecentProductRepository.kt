package woowacourse.shopping.repository.productRepository

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.domain.Product

interface RecentProductRepository {
    val recentProducts: Flow<List<Product>>
    suspend fun addRecentProduct(product: Product)
}
