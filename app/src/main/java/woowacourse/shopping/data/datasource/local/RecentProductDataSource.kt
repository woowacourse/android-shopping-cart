package woowacourse.shopping.data.datasource.local

import woowacourse.shopping.data.entity.RecentlyViewedProduct

interface RecentProductDataSource {
    fun getProducts(): List<RecentlyViewedProduct>

    fun getMostRecentProduct(): RecentlyViewedProduct?

    fun getOldestProduct(): RecentlyViewedProduct

    fun getCount(): Int

    fun insert(product: RecentlyViewedProduct)

    fun delete(product: RecentlyViewedProduct)
}
