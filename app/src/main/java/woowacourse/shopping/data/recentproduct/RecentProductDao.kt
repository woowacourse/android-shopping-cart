package woowacourse.shopping.data.recentproduct

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recentProduct: RecentProduct): Long

    @Query("SELECT * FROM recent_products ORDER BY recentTime DESC LIMIT 1")
    fun findMostRecentProduct(): RecentProduct?

    @Query("SELECT * FROM recent_products ORDER BY recentTime DESC")
    fun findAll(): List<RecentProduct>

    @Query("DELETE FROM recent_products")
    fun deleteAll()
}
