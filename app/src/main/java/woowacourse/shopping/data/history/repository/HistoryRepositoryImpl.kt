package woowacourse.shopping.data.history.repository

import woowacourse.shopping.data.history.HistoryDao
import woowacourse.shopping.data.toDomain
import woowacourse.shopping.data.toEntity
import woowacourse.shopping.domain.model.History
import kotlin.concurrent.thread

class HistoryRepositoryImpl(
    private val dao: HistoryDao,
) : HistoryRepository {
    override fun getAll(onSuccess: (List<History>) -> Unit) {
        thread {
            val history = dao.getAll().map { it.toDomain() }
            onSuccess(history)
        }
    }

    override fun insert(history: History) {
        thread {
            val isNew = dao.findById(history.id) == null
            if (isNew && dao.getAll().size >= 10) {
                dao.deleteOldest()
            }
            dao.insert(history.toEntity())
        }
    }

    override fun findLatest(onSuccess: (History?) -> Unit) {
        thread {
            val lastViewed = dao.findLatest()
            onSuccess(lastViewed?.toDomain())
        }
    }
}
