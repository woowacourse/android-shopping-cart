package woowacourse.shopping.data.datasource.recent

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.data.local.recent.RecentProductDao
import woowacourse.shopping.data.local.recent.RecentProductEntity

class RoomRecentProductDataSource(
    private val recentProductDao: RecentProductDao
) : RecentProductDataSource {

    override fun getRecentProducts(limit: Int): Flow<List<RecentProductEntity>> =
        recentProductDao.getRecentProducts(limit)

    override suspend fun getMostRecentProduct(): RecentProductEntity? =
        recentProductDao.getMostRecentProduct()

    override suspend fun upsert(recentProduct: RecentProductEntity) {
        recentProductDao.upsert(recentProduct)
    }

    override suspend fun deleteOlderThan(limit: Int) {
        recentProductDao.deleteOlderThan(limit)
    }
}
