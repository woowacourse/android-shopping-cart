package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.RecentProduct

interface RecentProductRepository {
    fun insert(productId: Long)

    fun getAll(): List<RecentProduct>

    fun getLastProduct(): RecentProduct?

    fun getPagedProducts(
        limit: Int,
        offset: Int = 0,
    ): List<RecentProduct>

    fun replaceRecentProduct(productId: Long)
}
