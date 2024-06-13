package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.RecentProduct

interface RecentProductRepository {
    fun recentProducts(size: Int): List<RecentProduct>

    fun addRecentProduct(productId: Long)

    fun lastViewedProduct(): RecentProduct?
}
