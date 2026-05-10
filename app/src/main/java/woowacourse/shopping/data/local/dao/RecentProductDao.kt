package woowacourse.shopping.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.data.local.entity.RecentProductEntity

@Dao
interface RecentProductDao {
    @Query("SELECT * FROM recent_products ORDER BY viewedAt DESC LIMIT 10")
    fun getRecentProducts(): Flow<List<RecentProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(product: RecentProductEntity)
}
