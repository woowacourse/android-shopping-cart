package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.RecentProductEntity

interface RecentProductLocalDataSource {
    fun getRecentProducts(limit: Int): List<RecentProductEntity>

    fun insertRecentProduct(recentProduct: RecentProductEntity)

    fun deleteByProductId(productId: Long)
}
