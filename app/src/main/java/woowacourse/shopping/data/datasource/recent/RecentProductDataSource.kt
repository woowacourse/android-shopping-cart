package woowacourse.shopping.data.datasource.recent

import woowacourse.shopping.data.db.producthistory.RecentProduct

interface RecentProductDataSource {
    fun fetchRecentProducts(): List<RecentProduct>?

    fun fetchMostRecentProduct(): RecentProduct?

    fun saveRecentProduct(productId: Long)
}
