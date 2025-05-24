package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.HistoryDataSource
import woowacourse.shopping.data.db.entity.HistoryEntity
import woowacourse.shopping.domain.repository.HistoryRepository
import kotlin.concurrent.thread

class HistoryRepositoryImpl(
    private val historyDataSource: HistoryDataSource,
) : HistoryRepository {
    override fun getHistory(onResult: (List<Long>) -> Unit) {
        thread {
            onResult(historyDataSource.latestHistory().map { it.productId })
        }
    }

    override fun saveHistory(
        productId: Long,
        onResult: () -> Unit,
    ) {
        thread {
            historyDataSource.insertHistory(HistoryEntity(productId))
            onResult()
        }
    }
}
