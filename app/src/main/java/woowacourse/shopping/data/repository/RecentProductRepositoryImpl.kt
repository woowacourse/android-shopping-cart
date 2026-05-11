package woowacourse.shopping.data.repository

import woowacourse.shopping.data.source.RecentProductSource
import woowacourse.shopping.domain.repository.RecentProductRepository

class RecentProductRepositoryImpl(
    private val dataSource: RecentProductSource,
) : RecentProductRepository {
    override fun getRecentProductIds(): List<String> = dataSource.getRecentProductIds()

    override fun getLastViewProductId(): String = dataSource.getRecentProductIds().first()

    override fun addRecentProductId(productId: String) {
        dataSource.addRecentProductId(productId = productId)
    }
}
