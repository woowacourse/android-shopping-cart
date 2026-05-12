package woowacourse.shopping.data.repository

import woowacourse.shopping.data.source.RecentProductSource
import woowacourse.shopping.domain.repository.RecentProductRepository

class RecentProductRepositoryImpl(
    private val dataSource: RecentProductSource,
) : RecentProductRepository {
    override suspend fun getRecentProductIds(): List<String> = dataSource.getRecentProductIds().map { it.productId }

    override suspend fun getLastViewProductId(): String? = dataSource.getLastViewProductId()?.productId

    override suspend fun addRecentProductId(productId: String) {
        dataSource.addRecentProductId(productId = productId)
    }
}
