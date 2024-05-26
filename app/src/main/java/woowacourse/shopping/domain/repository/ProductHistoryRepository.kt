package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.db.producthistory.ProductHistory

interface ProductHistoryRepository {
    fun getProductHistories(): List<ProductHistory>?

    fun getMostRecentProductHistory(): ProductHistory?

    fun setProductHistory(productId: Long)
}
