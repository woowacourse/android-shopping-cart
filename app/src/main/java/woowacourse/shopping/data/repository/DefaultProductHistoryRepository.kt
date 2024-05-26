package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.ProductHistoryDataSource
import woowacourse.shopping.data.db.producthistory.ProductHistory
import woowacourse.shopping.domain.repository.ProductHistoryRepository

class DefaultProductHistoryRepository(
    private val productHistoryDataSource: ProductHistoryDataSource,
) : ProductHistoryRepository {
    override fun getProductHistories(): List<ProductHistory>? {
        return productHistoryDataSource.fetchProductHistories()
    }

    override fun getMostRecentProductHistory(): ProductHistory? {
        return productHistoryDataSource.fetchMostRecentProductHistory()
    }

    override fun setProductHistory(productId: Long) {
        return productHistoryDataSource.saveProductHistory(productId)
    }
}
