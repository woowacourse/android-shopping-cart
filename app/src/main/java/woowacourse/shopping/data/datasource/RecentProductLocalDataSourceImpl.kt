package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.RecentProductDao
import woowacourse.shopping.data.db.RecentProductEntity

class RecentProductLocalDataSourceImpl(
    private val recentProductDao: RecentProductDao,
) : RecentProductLocalDataSource {
    override fun getRecentProducts(limit: Int): List<RecentProductEntity> = recentProductDao.getRecentProducts(limit)

    override fun insertRecentProduct(recentProduct: RecentProductEntity) {
        recentProductDao.insertRecentProduct(recentProduct)
    }

    override fun trimToLimit(limit: Int) {
        val savedRecentProductCount = recentProductDao.getRecentProductCount()
        if (savedRecentProductCount > limit) {
            val overflowCount = savedRecentProductCount - limit
            recentProductDao.deleteOldestRecentProducts(overflowCount)
        }
    }
}
