package woowacourse.shopping.data.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.source.local.recent.RecentProductDao
import woowacourse.shopping.data.source.local.recent.RecentProductEntity

class RecentProductSourceImpl(
    private val dao: RecentProductDao,
) : RecentProductSource {
    override fun getRecentProductIds(): Flow<List<RecentProductEntity>> = dao.getRecentProduct()

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

    override fun getLastViewProductId(): Flow<RecentProductEntity?> =
        dao
            .getRecentProduct()
            .map { products ->
                products.firstOrNull()
            }
}
