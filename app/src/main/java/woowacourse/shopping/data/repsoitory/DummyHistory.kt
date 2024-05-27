package woowacourse.shopping.data.repsoitory

import woowacourse.shopping.domain.model.History
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.HistoryRepository

object DummyHistory : HistoryRepository {
    private val histories: MutableList<History> = mutableListOf()

    override fun putProductOnHistory(product: Product) {
        val history = History(product, System.currentTimeMillis())
        histories.add(history)
    }

    override fun getHistories(size: Int): List<History> {
        val from = 0
        val to = if (size > histories.size) histories.size else size
        return histories.subList(from, to)
    }
}
