package woowacourse.shopping.data.source.local.recent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentProductDao {
    @Query("SELECT * FROM recent_products ORDER BY lastViewedAt DESC LIMIT :limit")
    suspend fun getRecent(limit: Int = 10): List<RecentProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: RecentProductEntity)

    @Query("DELETE FROM recent_products WHERE productId = :id")
    suspend fun delete(id: String)
}
