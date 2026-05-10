package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import woowacourse.shopping.data.entity.RecentProductEntity

@Dao
interface RecentProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recentProduct: RecentProductEntity)

    @Query(
        """
         DELETE FROM recent_product_table
            WHERE productId NOT IN (
           SELECT productId FROM recent_product_table 
             ORDER BY timestamp DESC 
             LIMIT 10
        )
     """,
    )
    suspend fun pruneOldItems()

    @Transaction
    suspend fun addRecentProduct(productId: String) {
        insert(RecentProductEntity(productId))
        pruneOldItems()
    }

    @Query("SELECT * FROM recent_product_table ORDER BY timestamp DESC")
    suspend fun getAllRecentProducts(): List<RecentProductEntity>

    @Query("SELECT * FROM recent_product_table ORDER BY timestamp DESC LIMIT 1")
    suspend fun getMostRecentProduct(): RecentProductEntity?
}
