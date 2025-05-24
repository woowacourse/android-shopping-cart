package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.RecentlyViewedProduct

interface RecentProductDataSource {
    fun getProducts(): List<RecentlyViewedProduct>

    fun getMostRecentProduct(): RecentlyViewedProduct?

    fun getOldestProduct(): RecentlyViewedProduct

    fun getCount(): Int

    fun insert(product: RecentlyViewedProduct)

    fun delete(product: RecentlyViewedProduct)
}
