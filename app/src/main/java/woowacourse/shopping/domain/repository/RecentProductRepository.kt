package woowacourse.shopping.domain.repository

import kotlinx.collections.immutable.ImmutableList
import woowacourse.shopping.domain.model.Product

interface RecentProductRepository {
    suspend fun upsertRecentProduct(productId: String)

    suspend fun getRecentProducts(limit: Int): ImmutableList<Product>
}
