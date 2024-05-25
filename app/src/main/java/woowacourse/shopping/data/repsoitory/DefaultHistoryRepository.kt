package woowacourse.shopping.data.repsoitory

import woowacourse.shopping.data.datasource.history.LocalHistoryDataSource
import woowacourse.shopping.domain.model.History
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.HistoryRepository

class DefaultHistoryRepository(
    private val localHistoryDataSource: LocalHistoryDataSource,
) : HistoryRepository {
    override fun putProductOnHistory(product: Product) {
        val timestamp = System.currentTimeMillis()
        val history = History(product, timestamp)
        localHistoryDataSource.putHistory(history)
    }

    override fun getHistories(size: Int): List<History> = localHistoryDataSource.getHistories(size)
}
