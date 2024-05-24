package woowacourse.shopping.data.datasourceimpl

import woowacourse.shopping.data.datasource.ProductHistoryDataSource
import woowacourse.shopping.db.producthistory.ProductHistory
import woowacourse.shopping.db.producthistory.ProductHistoryDatabase
import kotlin.concurrent.thread

class DefaultProductHistoryDatasource(
    productHistoryDatabase: ProductHistoryDatabase,
) : ProductHistoryDataSource {
    private val productHistoryDao = productHistoryDatabase.productHistoryDao()

    override fun fetchProductHistories(): List<ProductHistory>? {
        var productHistories: List<ProductHistory>? = null

        thread {
            productHistories = productHistoryDao.getAll()
        }.join()

        return productHistories
    }

    override fun fetchMostRecentProductHistory(): ProductHistory? {
        var productHistory: ProductHistory? = null

        thread {
            productHistory = productHistoryDao.getMostRecentProductHistory()
        }.join()

        return productHistory
    }

    override fun saveProductHistory(productId: Long) {
        thread {
            productHistoryDao.insert(ProductHistory(productId = productId))
        }.join()
    }
}
