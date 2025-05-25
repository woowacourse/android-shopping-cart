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
        thread {
            val existing = dao.findById(history.id)
            if (existing == null) {
                if (dao.getAll().size >= 10) {
                    dao.deleteOldest()
                }
                dao.insert(history.toEntity())
            }
        }
    }

    override fun findLatest(callback: (History?) -> Unit) {
        thread {
            val lastViewed = dao.findLatest()
            callback(lastViewed?.toDomain())
        }
    }
}
