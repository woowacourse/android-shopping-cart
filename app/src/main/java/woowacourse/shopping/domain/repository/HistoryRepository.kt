package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.History
import woowacourse.shopping.domain.model.Product

interface HistoryRepository {
    fun putProductOnHistory(product: Product)

    fun getHistories(size: Int): List<History>
}
