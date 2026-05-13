package woowacourse.shopping.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentProductDao {
    @Query("SELECT * FROM recentproductentity ORDER BY viewed_at DESC LIMIT :limit")
    fun getRecentProducts(limit: Int): Flow<List<RecentProductEntity>>

    @Query("SELECT * FROM recentproductentity ORDER BY viewed_at DESC LIMIT 1")
    suspend fun getLatestViewedProduct(): RecentProductEntity?

    @Upsert
    suspend fun upsertRecentProduct(recentProduct: RecentProductEntity)
}
