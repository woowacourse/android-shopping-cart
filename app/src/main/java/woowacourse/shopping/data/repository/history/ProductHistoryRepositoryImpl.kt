package woowacourse.shopping.data.repository.history

import woowacourse.shopping.data.database.history.ProductHistoryDao
import woowacourse.shopping.data.model.history.ProductHistory
import woowacourse.shopping.data.model.history.RecentProduct
import woowacourse.shopping.domain.repository.history.ProductHistoryRepository

class ProductHistoryRepositoryImpl(
    private val historyDao: ProductHistoryDao,
) : ProductHistoryRepository {
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
