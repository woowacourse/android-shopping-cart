package woowacourse.shopping.data.datasource.history

import woowacourse.shopping.data.db.dao.HistoryDao
import woowacourse.shopping.data.db.mapper.toEntity
import woowacourse.shopping.data.db.mapper.toHistory
import woowacourse.shopping.domain.datasource.HistoryDataSource
import woowacourse.shopping.domain.model.History

class LocalHistoryDataSource(
    private val historyDao: HistoryDao,
) : HistoryDataSource {
    override fun putHistory(history: History) {
        historyDao.putHistory(history.toEntity())
    }

    override fun getHistories(size: Int): List<History> {
        return historyDao.getHistories(size).toHistory()
    }
}
