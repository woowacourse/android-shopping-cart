package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.HistoryProduct

interface HistoryRepository {
    fun fetchAllHistory(): List<HistoryProduct>

    fun fetchRecentHistory(): HistoryProduct?

    fun saveHistory(productId: Int)
}
