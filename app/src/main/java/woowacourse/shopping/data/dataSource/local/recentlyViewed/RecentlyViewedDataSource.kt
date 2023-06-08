package woowacourse.shopping.data.dataSource.local.recentlyViewed

import woowacourse.shopping.data.entity.RecentlyViewedEntity

interface RecentlyViewedDataSource {
    fun getRecentlyViewedProducts(unit: Int): List<RecentlyViewedEntity>
    fun getLastViewedProduct(): List<RecentlyViewedEntity>
    fun addRecentlyViewedProduct(productId: Long, unit: Int): Long
    fun deleteRecentlyViewedProduct(productId: Long)
}
