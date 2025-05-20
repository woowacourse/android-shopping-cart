package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.HistoryProduct

interface HistoryRepository {
    fun fetchAllSearchHistory(): List<HistoryProduct>

    fun saveSearchHistory(productId: Int)

    fun fetchRecentSearchHistory(): HistoryProduct
}
