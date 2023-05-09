package woowacourse.shopping.data.product.recentlyViewed

interface RecentlyViewedDataSource {
    fun getRecentlyViewedProducts(unit: Int): List<RecentlyViewedEntity>
    fun addRecentlyViewedProduct(productId: Long, unit: Int): Long
}
