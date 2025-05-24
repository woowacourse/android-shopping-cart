package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.RecentProductEntity

interface RecentProductLocalDataSource {
    fun getRecentProductCount(): Int

    fun getRecentProducts(limit: Int): List<RecentProductEntity>

    fun insertRecentProduct(recentProduct: RecentProductEntity)

    fun deleteOldestRecentProduct()
}
