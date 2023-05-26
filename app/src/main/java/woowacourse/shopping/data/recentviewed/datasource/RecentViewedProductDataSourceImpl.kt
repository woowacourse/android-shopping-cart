package woowacourse.shopping.data.recentviewed.datasource

import woowacourse.shopping.data.recentviewed.RecentViewedProductEntity
import woowacourse.shopping.data.recentviewed.cache.RecentViewedProductCache

class RecentViewedProductDataSourceImpl(
    private val recentViewedProductCache: RecentViewedProductCache
) : RecentViewedProductDataSource {

    override fun addToRecentViewedProduct(id: Int) {
        recentViewedProductCache.addToRecentViewedProduct(id)
    }

    override fun getRecentViewedProducts(): List<RecentViewedProductEntity> {

        return recentViewedProductCache.getRecentViewedProducts()
    }

    override fun removeRecentViewedProduct() {
        recentViewedProductCache.removeRecentViewedProduct()
    }

    override fun getLatestViewedProduct(): RecentViewedProductEntity? {

        return recentViewedProductCache.getLatestViewedProduct()
    }
}
