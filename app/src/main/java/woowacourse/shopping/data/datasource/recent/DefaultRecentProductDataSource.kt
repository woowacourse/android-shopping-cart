package woowacourse.shopping.data.datasource.recent

import woowacourse.shopping.data.db.producthistory.RecentProduct
import woowacourse.shopping.data.db.producthistory.RecentProductDatabase
import kotlin.concurrent.thread

class DefaultRecentProductDataSource(
    recentProductDatabase: RecentProductDatabase,
) : RecentProductDataSource {
    private val productHistoryDao = recentProductDatabase.productHistoryDao()

    override fun fetchRecentProducts(): List<RecentProduct>? {
        var productHistories: List<RecentProduct>? = null

        thread {
            productHistories = productHistoryDao.getAll()
        }.join()

        return productHistories
    }

    override fun fetchMostRecentProduct(): RecentProduct? {
        var recentProduct: RecentProduct? = null

        thread {
            recentProduct = productHistoryDao.getMostRecentProduct()
        }.join()

        return recentProduct
    }

    override fun saveRecentProduct(productId: Long) {
        thread {
            productHistoryDao.insert(RecentProduct(productId = productId))
        }.join()
    }
}
