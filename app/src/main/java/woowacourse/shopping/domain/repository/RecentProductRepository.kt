package woowacourse.shopping.domain.repository

import kotlinx.coroutines.flow.Flow

interface RecentProductRepository {
    fun getRecentProductIds(): Flow<List<String>>

    fun getLastViewProductId(): Flow<String?>

    suspend fun addRecentProductId(productId: String)
}
