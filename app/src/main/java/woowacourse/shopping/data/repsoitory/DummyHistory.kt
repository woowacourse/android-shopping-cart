package woowacourse.shopping.data.repsoitory

import woowacourse.shopping.domain.model.History
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.HistoryRepository

object DummyHistory : HistoryRepository {
    override fun putProductOnHistory(product: Product) {
        TODO("Not yet implemented")
    }

    override fun getHistories(size: Int): List<History> {
        TODO("Not yet implemented")
    }
}
