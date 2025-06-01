package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.dao.HistoryDao
import woowacourse.shopping.data.db.entity.HistoryEntity

class HistoryDataSource(
    private val dao: HistoryDao,
) {
    fun latestHistory(): List<HistoryEntity> = dao.getLatestHistory()

    fun insertHistory(entity: HistoryEntity) = dao.insert(entity)
}
