package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.CartableProduct
import woowacourse.shopping.data.model.ProductHistory
import woowacourse.shopping.data.model.RecentProduct

interface ProductHistoryRepository {
    fun addProductHistory(productHistory: ProductHistory): Long

    fun fetchProductHistory(size: Int): List<RecentProduct>

    fun fetchLatestHistory(): RecentProduct
}
