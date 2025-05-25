package woowacourse.shopping.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentProductDao {
    @Query("SELECT COUNT(*) FROM RecentViewedProducts")
    fun getRecentProductCount(): Int

    @Query("SELECT * FROM RecentViewedProducts ORDER BY lastViewedAt DESC LIMIT :limit")
    fun getRecentProducts(limit: Int): List<RecentProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecentProduct(recentProduct: RecentProductEntity)

    @Query(
        """
        DELETE FROM RecentViewedProducts 
        WHERE productId = (
            SELECT productId FROM RecentViewedProducts 
            ORDER BY lastViewedAt ASC 
            LIMIT 1
        )
    """,
    )
    fun deleteOldestRecentProduct()
}
