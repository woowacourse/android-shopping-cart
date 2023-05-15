package woowacourse.shopping.domain

data class RecentProducts(val value: List<RecentProduct> = emptyList()) {
    fun add(recentProduct: RecentProduct): RecentProducts {
        return RecentProducts(value + recentProduct)
    }

    fun makeRecentProduct(product: Product): RecentProduct {
        val ordinal = getCurrentOrdinal()
        return RecentProduct(ordinal, product)
    }

    private fun getCurrentOrdinal(): Int {
        return if (value.isEmpty()) 0 else value.maxOf { it.ordinal } + 1
    }

    fun getRecentProducts(size: Int): RecentProducts {
        return RecentProducts(value.takeLast(size))
    }
}
