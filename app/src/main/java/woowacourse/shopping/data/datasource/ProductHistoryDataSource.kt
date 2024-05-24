package woowacourse.shopping.data.datasource

import woowacourse.shopping.db.producthistory.ProductHistory

interface ProductHistoryDataSource {
    fun fetchProductHistories(): List<ProductHistory>?

    fun fetchMostRecentProductHistory(): ProductHistory?

    fun saveProductHistory(productId: Long)
}
