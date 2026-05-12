package woowacourse.shopping.repository

import woowacourse.shopping.model.ProductId
import woowacourse.shopping.model.RecentProduct

interface RecentProductRepository {
    suspend fun recordView(productId: ProductId)

    suspend fun getRecentProducts(limit: Int): List<RecentProduct>

    suspend fun getLatestViewedProductExcluding(productId: ProductId): RecentProduct?
}
