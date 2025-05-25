package woowacourse.shopping.data.recentlyproducts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentlyProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productId: Long)

    @Query("SELECT * FROM recently_products ORDER BY time DESC LIMIT 1")
    fun getRecent(): Long?

    @Query("SELECT * FROM recently_products ORDER BY time DESC")
    fun getAll(): List<Long>
}
