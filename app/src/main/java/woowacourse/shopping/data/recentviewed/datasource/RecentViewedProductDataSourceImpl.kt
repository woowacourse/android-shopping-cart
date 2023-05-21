package woowacourse.shopping.data.recentviewed.datasource

import android.content.Context
import woowacourse.shopping.data.recentviewed.RecentViewedProductEntity
import woowacourse.shopping.data.recentviewed.cache.RecentViewedProductCache
import woowacourse.shopping.data.recentviewed.cache.RecentViewedProductCacheImpl

class RecentViewedProductDataSourceImpl(
    context: Context,
    private val recentViewedProductCache: RecentViewedProductCache = RecentViewedProductCacheImpl(
        context
    ),
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
