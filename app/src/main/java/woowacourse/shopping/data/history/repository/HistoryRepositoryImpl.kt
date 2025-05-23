package woowacourse.shopping.data.history.repository

import woowacourse.shopping.data.history.HistoryDao
import woowacourse.shopping.data.toDomain
import woowacourse.shopping.data.toEntity
import woowacourse.shopping.domain.model.History
import kotlin.concurrent.thread

class HistoryRepositoryImpl(
    private val dao: HistoryDao,
) : HistoryRepository {
    override fun getAll(callback: (List<History>) -> Unit) {
        thread {
            val history = dao.getAll().map { it.toDomain() }
            callback(history)
        }
    }

    override fun insert(history: History) {
        dao.insert(history.toEntity())
    }
}
