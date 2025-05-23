package woowacourse.shopping.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentlyProductDao {
    @Query("SELECT * FROM recentlyViewedProduct ORDER BY viewedAt DESC")
    fun getProducts(): List<RecentlyViewedProduct>

    @Query("SELECT * FROM recentlyViewedProduct ORDER BY viewedAt DESC LIMIT 1")
    fun getMostRecentProduct(): RecentlyViewedProduct?

    @Query("SELECT * FROM recentlyViewedProduct ORDER BY viewedAt ASC LIMIT 1")
    fun getOldestProduct(): RecentlyViewedProduct

    @Query("SELECT COUNT(*) FROM recentlyViewedProduct")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: RecentlyViewedProduct)

    @Delete
    fun delete(product: RecentlyViewedProduct)
}
