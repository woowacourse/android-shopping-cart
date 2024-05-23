package woowacourse.shopping.data.recent

import woowacourse.shopping.model.RecentProduct

interface RecentProductRepository {
    fun findLastOrNull(): RecentProduct?

    fun findRecentProducts(): List<RecentProduct>

    fun save(productId: Long)
}
