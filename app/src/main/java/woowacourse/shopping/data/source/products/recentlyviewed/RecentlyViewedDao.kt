package woowacourse.shopping.data.source.products.recentlyviewed

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentlyViewedDao {
    @Query("SELECT productId FROM recently_viewed ORDER BY viewed_at DESC LIMIT 10")
    fun getRecentlyViewed(): List<Long>

    @Query("SELECT productId FROM recently_viewed ORDER BY viewed_at DESC LIMIT 1")
    fun getLatestViewed(): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recentlyViewedEntity: RecentlyViewedEntity)

    @Query("DELETE FROM recently_viewed WHERE viewed_at = (SELECT MIN(viewed_at) FROM recently_viewed)")
    fun deleteOldestViewed()

    @Query("SELECT COUNT(*) FROM recently_viewed")
    fun getCount(): Int
}
