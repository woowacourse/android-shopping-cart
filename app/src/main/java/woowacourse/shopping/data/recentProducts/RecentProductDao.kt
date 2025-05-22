package woowacourse.shopping.data.recentProducts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecentProductDao {
    @Query("SELECT * FROM recent_product")
    fun getAll(): List<RecentProductEntity>

    @Insert
    fun insert(recentProduct: RecentProductEntity)
}
