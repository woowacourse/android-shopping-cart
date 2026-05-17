package woowacourse.shopping.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.source.RecentProductSource
import woowacourse.shopping.domain.repository.RecentProductRepository

class RecentProductRepositoryImpl(
    private val dataSource: RecentProductSource,
) : RecentProductRepository {
    override fun getRecentProductIds(): Flow<List<String>> =
        dataSource
            .getRecentProductIds()
            .map { products ->
                products.map {
                    it.productId
                }
            }

    override fun getLastViewProductId(): Flow<String?> =
        dataSource
            .getLastViewProductId()
            .map { it?.productId }

    override suspend fun addRecentProductId(productId: String) {
        dataSource.addRecentProductId(productId = productId)
    }
}
