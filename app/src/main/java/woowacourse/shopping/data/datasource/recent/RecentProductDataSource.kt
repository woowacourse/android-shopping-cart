package woowacourse.shopping.data.datasource.recent

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.data.local.recent.RecentProductEntity

interface RecentProductDataSource {
    fun getRecentProducts(limit: Int): Flow<List<RecentProductEntity>>

    suspend fun getMostRecentProduct(): RecentProductEntity?

    suspend fun upsert(recentProduct: RecentProductEntity)

    suspend fun deleteOlderThan(limit: Int)
}
