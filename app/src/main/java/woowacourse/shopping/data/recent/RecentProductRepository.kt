package woowacourse.shopping.data.recent

import woowacourse.shopping.domain.RecentProduct

interface RecentProductRepository {
    fun recentProducts(): List<RecentProduct>
    fun addRecentProduct(productId: Long)
    fun lastViewedProduct(): RecentProduct?
}
