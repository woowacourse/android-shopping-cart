package woowacourse.shopping.domain.repository.history

import woowacourse.shopping.data.model.history.ProductHistory
import woowacourse.shopping.data.model.history.RecentProduct

interface ProductHistoryRepository {
    fun addProductHistory(productHistory: ProductHistory)

    fun fetchProductHistory(size: Int): List<RecentProduct>

    fun fetchLatestHistory(): RecentProduct?
}
