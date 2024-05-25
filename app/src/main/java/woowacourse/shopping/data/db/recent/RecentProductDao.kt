package woowacourse.shopping.data.db.recent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentProductDao {
    @Query("SELECT * FROM recentProducts ORDER BY datetime(date_time) DESC LIMIT 10;")
    fun loadAll(): List<RecentProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recentProduct: RecentProductEntity): Long

    @Query("SELECT * FROM recentProducts ORDER BY datetime(date_time) DESC LIMIT 1;")
    fun getMostRecent(): RecentProductEntity?
}
