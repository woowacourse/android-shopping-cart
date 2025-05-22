package woowacourse.shopping.data.recent

import woowacourse.shopping.domain.RecentProduct

interface RecentProductRepository {
    fun insert(productId: Long)

    fun getAll(): List<RecentProduct>

    fun getPagedProducts(
        limit: Int,
        offset: Int = 0,
    ): List<RecentProduct>

    fun replaceRecentProduct(productId: Long)
}
