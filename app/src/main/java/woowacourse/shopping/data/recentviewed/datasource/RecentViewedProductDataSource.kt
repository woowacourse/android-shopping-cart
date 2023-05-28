package woowacourse.shopping.data.recentviewed.datasource

import woowacourse.shopping.data.recentviewed.RecentViewedProductEntity

interface RecentViewedProductDataSource {

    fun addToRecentViewedProduct(id: Int)

    fun getRecentViewedProducts(): List<RecentViewedProductEntity>

    fun removeRecentViewedProduct()

    fun getLatestViewedProduct(): RecentViewedProductEntity?
}
