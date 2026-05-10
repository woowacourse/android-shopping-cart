package woowacourse.shopping.domain.repository

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.domain.RecentProduct

interface ProductRecentRepository {
    fun getRecentProducts(limit: Int): Flow<List<RecentProduct>>

    suspend fun insertRecentProduct(productId: String)
}
