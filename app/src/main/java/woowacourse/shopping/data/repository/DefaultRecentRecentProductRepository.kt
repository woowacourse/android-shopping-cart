package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.recent.RecentProductDataSource
import woowacourse.shopping.data.db.producthistory.RecentProduct
import woowacourse.shopping.domain.repository.RecentProductRepository

class DefaultRecentRecentProductRepository(
    private val recentProductDataSource: RecentProductDataSource,
) : RecentProductRepository {
    override fun getProductHistories(): List<RecentProduct>? {
        return recentProductDataSource.fetchRecentProducts()
    }

    override fun getMostRecentProductHistory(): RecentProduct? {
        return recentProductDataSource.fetchMostRecentProduct()
    }

    override fun setProductHistory(productId: Long) {
        return recentProductDataSource.saveRecentProduct(productId)
    }
}
