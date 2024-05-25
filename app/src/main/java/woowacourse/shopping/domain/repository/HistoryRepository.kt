package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.History
import woowacourse.shopping.domain.model.Product

interface HistoryRepository {
    fun putRecentProductOnHistory(product: Product)

    fun getHistories(): List<History>
}
