package woowacourse.shopping.data.source.local.recent

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface RecentProductDao {
    @Query("SELECT * from recentProduct ORDER BY view_time DESC LIMIT 10")
    suspend fun getRecentProduct(): List<RecentProductEntity>

    @Upsert
    suspend fun upsert(recentProduct: RecentProductEntity)

    @Query(
        """
    DELETE FROM recentProduct
    WHERE product_id NOT IN (
        SELECT product_id FROM recentProduct
        ORDER BY view_time DESC
        LIMIT 10
    )
""",
    )
    suspend fun deleteOldItems()
}
