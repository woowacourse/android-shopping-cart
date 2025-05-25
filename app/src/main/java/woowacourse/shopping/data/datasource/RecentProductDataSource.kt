package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.RecentlyViewedProduct

interface RecentProductDataSource {
    fun getProducts(): Result<List<RecentlyViewedProduct>>

    fun getMostRecentProduct(): Result<RecentlyViewedProduct?>

    fun getOldestProduct(): Result<RecentlyViewedProduct>

    fun getCount(): Result<Int>

    fun insert(product: RecentlyViewedProduct): Result<Unit>

    fun delete(product: RecentlyViewedProduct): Result<Unit>
}
