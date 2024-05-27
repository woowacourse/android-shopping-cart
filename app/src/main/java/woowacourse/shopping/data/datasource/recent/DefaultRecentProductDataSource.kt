package woowacourse.shopping.data.datasource.recent

import woowacourse.shopping.data.db.producthistory.ProductHistory
import woowacourse.shopping.data.db.producthistory.ProductHistoryDatabase
import kotlin.concurrent.thread

class DefaultRecentProductDataSource(
    productHistoryDatabase: ProductHistoryDatabase,
) : RecentProductDataSource {
    private val productHistoryDao = productHistoryDatabase.productHistoryDao()

    override fun fetchRecentProducts(): List<ProductHistory>? {
        var productHistories: List<ProductHistory>? = null

        thread {
            productHistories = productHistoryDao.getAll()
        }.join()

        return productHistories
    }

    override fun fetchMostRecentProduct(): ProductHistory? {
        var productHistory: ProductHistory? = null

        thread {
            productHistory = productHistoryDao.getMostRecentProductHistory()
        }.join()

        return productHistory
    }

    override fun saveRecentProduct(productId: Long) {
        thread {
            productHistoryDao.insert(ProductHistory(productId = productId))
        }.join()
    }
}
