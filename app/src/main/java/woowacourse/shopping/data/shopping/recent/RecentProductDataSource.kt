package woowacourse.shopping.data.shopping.recent

interface RecentProductDataSource {
    fun recentProducts(size: Int): Result<List<RecentProductData>>

    fun saveRecentProduct(product: RecentProductData): Result<Long>
}
