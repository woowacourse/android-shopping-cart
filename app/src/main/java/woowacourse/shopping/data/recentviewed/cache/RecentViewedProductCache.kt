package woowacourse.shopping.data.recentviewed.cache

import woowacourse.shopping.data.recentviewed.RecentViewedProductEntity

interface RecentViewedProductCache {

    fun addToRecentViewedProduct(id: Int)

    fun getRecentViewedProducts(): List<RecentViewedProductEntity>

    fun removeRecentViewedProduct()

    fun getLatestViewedProduct(): RecentViewedProductEntity?
}
