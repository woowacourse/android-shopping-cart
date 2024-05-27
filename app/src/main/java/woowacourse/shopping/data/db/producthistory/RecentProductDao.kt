package woowacourse.shopping.data.db.producthistory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentProductDao {
    @Query("SELECT * FROM recent_product ORDER BY id DESC LIMIT 10")
    fun getAll(): List<RecentProduct>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recentProduct: RecentProduct): Long

    @Query("SELECT * FROM recent_product ORDER BY id DESC LIMIT 1")
    fun getMostRecentProduct(): RecentProduct?
}
