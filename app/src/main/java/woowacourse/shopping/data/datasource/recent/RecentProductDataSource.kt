package woowacourse.shopping.data.datasource.recent

import woowacourse.shopping.data.db.producthistory.ProductHistory

interface RecentProductDataSource {
    fun fetchRecentProducts(): List<ProductHistory>?

    fun fetchMostRecentProduct(): ProductHistory?

    fun saveRecentProduct(productId: Long)
}
