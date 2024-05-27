package woowacourse.shopping.data.local

import woowacourse.shopping.data.database.history.ProductHistoryDao
import woowacourse.shopping.data.datasource.ProductHistoryDataSource
import woowacourse.shopping.data.model.history.ProductHistory
import woowacourse.shopping.data.model.history.RecentProduct

class ProductHistoryLocalDataSource(
    private val historyDao: ProductHistoryDao,
) : ProductHistoryDataSource {
    override fun addProductHistory(productHistory: ProductHistory) {
        return historyDao.addProductHistory(productHistory)
    }

    override fun fetchProductHistory(size: Int): List<RecentProduct> {
        return historyDao.getProductHistory(size)
    }

    override fun fetchLatestHistory(): RecentProduct? {
        return historyDao.getLastProduct()
    }
}

