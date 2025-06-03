package woowacourse.shopping.fixture

import woowacourse.shopping.data.history.repository.HistoryRepository
import woowacourse.shopping.domain.model.Cart

class FakeHistoryRepository : HistoryRepository {
    val historyList = mutableListOf<Cart>()
    var savedHistory: Cart? = null

    override fun getAll(callback: (List<Cart>) -> Unit) {
        callback(historyList.toList())
    }

    override fun insert(history: Cart) {
        if (historyList.none { it.goods.id == history.goods.id }) {
            if (historyList.size == 10) {
                historyList.removeAt(0)
            }
            historyList.add(history)
            savedHistory = history
        }
    }

    override fun findLatest(callback: (Cart?) -> Unit) {
        callback(historyList.lastOrNull())
    }
}
