package woowacourse.shopping.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentProductDao {
    @Query("SELECT * FROM recent_products ORDER BY timestamp DESC LIMIT 10")
    fun getRecentProducts(): Flow<List<RecentProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recentProduct: RecentProductEntity)

    @Query("DELETE FROM recent_products WHERE id NOT IN (SELECT id FROM recent_products ORDER BY timestamp DESC LIMIT 10)")
    suspend fun deleteOldProducts()

    @Transaction
    suspend fun insertWithLimit(recentProduct: RecentProductEntity) {
        insert(recentProduct)
        deleteOldProducts()
    }
}
