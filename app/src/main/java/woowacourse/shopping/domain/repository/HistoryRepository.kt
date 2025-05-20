package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.HistoryProduct

interface HistoryRepository {
    fun fetchAllSearchHistory(callback: (List<HistoryProduct>) -> Unit)

    fun saveSearchHistory(productId: Int)

    fun fetchRecentSearchHistory(callback: (HistoryProduct) -> Unit)
}
