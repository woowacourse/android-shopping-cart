package woowacourse.shopping.data.repsoitory

import woowacourse.shopping.data.datasource.history.LocalHistoryDataSource
import woowacourse.shopping.domain.model.History
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.HistoryRepository
import woowacourse.shopping.presentation.common.runOnOtherThread
import woowacourse.shopping.presentation.common.runOnOtherThreadAndReturn

class DefaultHistoryRepository(
    private val localHistoryDataSource: LocalHistoryDataSource,
) : HistoryRepository {
    override fun putProductOnHistory(product: Product) =
        runOnOtherThread {
            val timestamp = System.currentTimeMillis()
            val history = History(product, timestamp)
            localHistoryDataSource.putHistory(history)
        }

    override fun getHistories(size: Int): List<History> =
        runOnOtherThreadAndReturn {
            localHistoryDataSource.getHistories(size)
        }
}
