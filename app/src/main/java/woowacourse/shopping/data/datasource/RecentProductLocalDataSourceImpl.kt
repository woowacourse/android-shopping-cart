package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.RecentProductDao
import woowacourse.shopping.data.db.RecentProductEntity

class RecentProductLocalDataSourceImpl(
    private val recentProductDao: RecentProductDao,
) : RecentProductLocalDataSource {
    override fun getRecentProductCount(): Int = recentProductDao.getRecentProductCount()

    override fun getRecentProducts(limit: Int): List<RecentProductEntity> = recentProductDao.getRecentProducts(limit)

    override fun insertRecentProduct(recentProduct: RecentProductEntity) {
        recentProductDao.insertRecentProduct(recentProduct)
    }

    override fun deleteOldestRecentProduct() {
        recentProductDao.deleteOldestRecentProduct()
    }
}
