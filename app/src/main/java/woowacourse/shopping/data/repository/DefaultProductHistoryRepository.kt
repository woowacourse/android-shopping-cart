package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.recent.RecentProductDataSource
import woowacourse.shopping.data.db.producthistory.ProductHistory
import woowacourse.shopping.domain.repository.ProductHistoryRepository

class DefaultProductHistoryRepository(
    private val recentProductDataSource: RecentProductDataSource,
) : ProductHistoryRepository {
    override fun getProductHistories(): List<ProductHistory>? {
        return recentProductDataSource.fetchRecentProducts()
    }

    override fun getMostRecentProductHistory(): ProductHistory? {
        return recentProductDataSource.fetchMostRecentProduct()
    }

    override fun setProductHistory(productId: Long) {
        return recentProductDataSource.saveRecentProduct(productId)
    }
}
