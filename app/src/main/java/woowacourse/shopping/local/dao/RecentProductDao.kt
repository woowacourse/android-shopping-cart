package woowacourse.shopping.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.local.entity.RecentProductEntity

@Dao
interface RecentProductDao {
    @Query("SELECT * FROM recent_products ORDER BY viewedAt DESC LIMIT 10")
    suspend fun getRecentItems(): List<RecentProductEntity>

    @Query("SELECT * FROM recent_products ORDER BY viewedAt DESC LIMIT 1")
    suspend fun getLastItem(): RecentProductEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recentProductEntity: RecentProductEntity)

    @Query("DELETE FROM recent_products WHERE productId NOT IN (SELECT productId FROM recent_products ORDER BY viewedAt DESC LIMIT 10)")
    suspend fun deleteOldItems()
}
