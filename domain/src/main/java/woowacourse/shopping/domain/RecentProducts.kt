package woowacourse.shopping.domain

import java.time.LocalDateTime

data class RecentProducts(val value: List<RecentProduct> = emptyList()) {
    fun add(recentProduct: RecentProduct): RecentProducts {
        return RecentProducts(listOf(recentProduct) + value)
    }

    fun makeRecentProduct(product: Product): RecentProduct {
        return RecentProduct(LocalDateTime.now(), product)
    }

    fun getRecentProducts(size: Int): RecentProducts {
        return RecentProducts(value.take(size))
    }

    fun update(recentProduct: RecentProduct): RecentProducts {
        return RecentProducts(listOf(recentProduct) + value.filter { it.product != recentProduct.product })
    }
}
