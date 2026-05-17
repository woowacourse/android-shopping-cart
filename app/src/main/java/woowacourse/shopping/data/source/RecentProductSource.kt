package woowacourse.shopping.data.source

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.data.source.local.recent.RecentProductEntity

interface RecentProductSource {
    fun getRecentProductIds(): Flow<List<RecentProductEntity>>

    suspend fun addRecentProductId(productId: String)

    fun getLastViewProductId(): Flow<RecentProductEntity?>
}
