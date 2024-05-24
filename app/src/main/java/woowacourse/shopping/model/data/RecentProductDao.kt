package woowacourse.shopping.model.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentProductDao {
    @Query("SELECT * FROM recentProducts ORDER BY createdTime DESC")
    fun getAll(): List<RecentProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recentProduct: RecentProductEntity)
}
