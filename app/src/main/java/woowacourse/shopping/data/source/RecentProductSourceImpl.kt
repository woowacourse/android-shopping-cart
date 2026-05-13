package woowacourse.shopping.data.source

import woowacourse.shopping.data.source.local.recent.RecentProductDao
import woowacourse.shopping.data.source.local.recent.RecentProductEntity

class RecentProductSourceImpl(
    private val dao: RecentProductDao,
) : RecentProductSource {
    override suspend fun getRecentProductIds(): List<RecentProductEntity> = dao.getRecentProduct()

    override suspend fun addRecentProductId(productId: String) {
        val viewTime = System.currentTimeMillis()

        dao.upsert(
            recentProduct = RecentProductEntity(
                productId = productId,
                viewTime = viewTime,
            ),
        )

        dao.deleteOldItems()
    }

    override suspend fun getLastViewProductId(): RecentProductEntity? = getRecentProductIds().firstOrNull()
}
