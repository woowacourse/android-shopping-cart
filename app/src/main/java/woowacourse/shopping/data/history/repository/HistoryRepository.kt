package woowacourse.shopping.data.history.repository

import woowacourse.shopping.domain.model.History

interface HistoryRepository {
    fun getAll(onSuccess: (List<History>) -> Unit)

    fun insert(history: History)

    fun findLatest(onSuccess: (History?) -> Unit)
}
