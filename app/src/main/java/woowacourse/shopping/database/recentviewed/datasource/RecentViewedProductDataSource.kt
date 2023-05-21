package woowacourse.shopping.database.recentviewed.datasource

import woowacourse.shopping.database.recentviewed.RecentViewedProductEntity

interface RecentViewedProductDataSource {

    fun addToRecentViewedProduct(id: Int)

    fun getRecentViewedProducts(): List<RecentViewedProductEntity>

    fun removeRecentViewedProduct()

    fun getLatestViewedProduct(): RecentViewedProductEntity?
}
