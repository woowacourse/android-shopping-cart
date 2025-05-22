package woowacourse.shopping.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentProductDao {
    @Query("SELECT * FROM RecentViewedProducts ORDER BY lastViewedAt ASC LIMIT :limit")
    fun getRecentProducts(limit: Int): List<RecentProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecentProduct(recentProduct: RecentProductEntity)

    @Query("DELETE FROM RecentViewedProducts WHERE productId = :productId")
    fun deleteByProductId(productId: Long)
}
