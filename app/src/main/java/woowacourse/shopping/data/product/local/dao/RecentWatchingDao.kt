package woowacourse.shopping.data.product.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.product.local.entity.RecentWatchingEntity

@Dao
interface RecentWatchingDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    fun insertRecentWatching(recentWatchingEntity: RecentWatchingEntity)

    @Query("SELECT * FROM recent_watching ORDER BY watchedAt DESC LIMIT :size")
    fun getRecentWatchingProducts(size: Int): List<RecentWatchingEntity>
}
