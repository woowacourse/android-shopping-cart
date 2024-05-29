package woowacourse.shopping.model.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentProductDao {
    @Query("SELECT * FROM recentProducts ORDER BY createdTime DESC")
    fun getAll(): List<RecentProductEntity>

    @Query("SELECT * FROM recentProducts ORDER BY createdTime DESC LIMIT 1 OFFSET 1")
    fun getSecondLast(): RecentProductEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recentProduct: RecentProductEntity)
}
