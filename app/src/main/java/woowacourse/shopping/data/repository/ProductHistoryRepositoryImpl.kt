package woowacourse.shopping.data.repository

import woowacourse.shopping.data.local.ProductHistoryDao
import woowacourse.shopping.data.model.CartableProduct
import woowacourse.shopping.data.model.ProductHistory
import woowacourse.shopping.data.model.RecentProduct
import woowacourse.shopping.domain.repository.ProductHistoryRepository

class ProductHistoryRepositoryImpl(
    private val historyDao: ProductHistoryDao
) : ProductHistoryRepository {
    override fun addProductHistory(productHistory: ProductHistory): Long {
        return historyDao.insertProductHistory(productHistory)
    }

    override fun fetchProductHistory(size: Int): List<RecentProduct> {
        return historyDao.getProductHistory(size)
    }
}
