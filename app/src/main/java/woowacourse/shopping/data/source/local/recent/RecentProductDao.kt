package woowacourse.shopping.data.source.local.recent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecentProductDao {
    @Query("SELECT * from recentProduct LIMIT 10")
    suspend fun getRecentProduct(): List<RecentProductEntity>

    @Insert
    suspend fun insert(recentProduct: RecentProductEntity)

    @Update
    suspend fun update(recentProduct: RecentProductEntity)

    @Query("DELETE FROM recentProduct WHERE product_id = :productId")
    suspend fun delete(productId: String)
}
