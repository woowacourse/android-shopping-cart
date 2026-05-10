package woowacourse.shopping.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.local.entity.RecentItemEntity

@Dao
interface RecentItemDao {
    @Query("SELECT * FROM recent_items ORDER BY viewedAt DESC LIMIT 10")
    suspend fun getRecentItems(): List<RecentItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recentItemEntity: RecentItemEntity)

    @Query("DELETE FROM recent_items WHERE productId NOT IN (SELECT productId FROM recent_items ORDER BY viewedAt DESC LIMIT 10)")
    suspend fun deleteOldItems()
}
