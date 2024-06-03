package woowacourse.shopping.data.datasource.productbrowsinghistory

import woowacourse.shopping.data.db.dao.ProductBrowsingHistoryDao
import woowacourse.shopping.data.db.mapper.toEntity
import woowacourse.shopping.data.db.mapper.toHistory
import woowacourse.shopping.domain.datasource.HistoryDataSource
import woowacourse.shopping.domain.model.ProductBrowsingHistory

class LocalProductBrowsingHistory(
    private val productBrowsingHistoryDao: ProductBrowsingHistoryDao,
) : HistoryDataSource {
    override fun putHistory(productBrowsingHistory: ProductBrowsingHistory) {
        productBrowsingHistoryDao.putHistory(productBrowsingHistory.toEntity())
    }

    override fun getHistories(size: Int): List<ProductBrowsingHistory> {
        return productBrowsingHistoryDao.getHistories(size).toHistory()
    }
}
