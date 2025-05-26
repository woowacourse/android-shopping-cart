package woowacourse.shopping.fixture

import woowacourse.shopping.data.history.repository.HistoryRepository

class FakeHistoryRepository : HistoryRepository {
    val historyList = mutableListOf<History>()
    var savedHistory: History? = null

    override fun getAll(callback: (List<History>) -> Unit) {
        callback(historyList.toList())
    }

    override fun insert(history: History) {
        if (historyList.none { it.id == history.id }) {
            if (historyList.size == 10) {
                historyList.removeAt(0)
            }
            historyList.add(history)
            savedHistory = history
        }
    }

    override fun findLatest(callback: (History?) -> Unit) {
        callback(historyList.lastOrNull())
    }
}
