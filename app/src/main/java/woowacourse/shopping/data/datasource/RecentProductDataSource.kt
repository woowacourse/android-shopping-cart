package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.RecentlyViewedProduct

interface RecentProductDataSource {
    fun getProducts(): List<RecentlyViewedProduct>

    fun insert(product: RecentlyViewedProduct)
}
