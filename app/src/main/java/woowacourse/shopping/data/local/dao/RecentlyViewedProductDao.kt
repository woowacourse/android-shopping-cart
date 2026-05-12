package woowacourse.shopping.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.data.local.entity.RecentlyViewedProductEntity

@Dao
interface RecentlyViewedProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recentlyViewedProductEntity: RecentlyViewedProductEntity)

    @Query(
        "DELETE FROM recently_viewed_products WHERE id NOT IN " +
            "(SELECT id FROM recently_viewed_products ORDER BY time_stamp DESC LIMIT 10)",
    )
    suspend fun removeOldData()

    @Transaction
    suspend fun enqueueAndLimit10(recentlyViewedProductEntity: RecentlyViewedProductEntity) {
        insert(recentlyViewedProductEntity)
        removeOldData()
    }

    @Query("SELECT * FROM recently_viewed_products ORDER BY time_stamp DESC")
    fun getAll(): Flow<List<RecentlyViewedProductEntity>?>

    @Query("SELECT id FROM recently_viewed_products ORDER BY time_stamp DESC LIMIT 1")
    fun getLatestItemId(): Flow<String?>
}
