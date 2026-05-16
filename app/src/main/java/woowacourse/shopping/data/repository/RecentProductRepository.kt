package woowacourse.shopping.data.repository

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Products
import java.util.UUID

interface RecentProductRepository {
    suspend fun getRecentProducts(): Products

    suspend fun getLastViewedProduct(): Product?

    suspend fun add(productId: UUID)

    fun observeRecent(): Flow<List<Product>>
}
