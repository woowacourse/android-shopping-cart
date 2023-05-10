package woowacourse.shopping.domain

data class RecentProducts(val recentProducts: List<RecentProduct>) {
    fun add(recentProduct: RecentProduct): RecentProducts {
        return RecentProducts(recentProducts + recentProduct)
    }

    fun getRecentProducts(size: Int): RecentProducts {
        return RecentProducts(recentProducts.take(size))
    }
}
