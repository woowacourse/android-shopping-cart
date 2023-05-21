package woowacourse.shopping.database.recentviewed.datasource

import android.content.Context
import woowacourse.shopping.database.recentviewed.RecentViewedProductEntity
import woowacourse.shopping.database.recentviewed.cache.RecentViewedProductCache
import woowacourse.shopping.database.recentviewed.cache.RecentViewedProductCacheImpl

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
