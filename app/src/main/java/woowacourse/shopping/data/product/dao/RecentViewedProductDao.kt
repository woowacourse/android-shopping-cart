package woowacourse.shopping.data.product.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.product.entity.RecentViewedProductEntity

@Dao
interface RecentViewedProductDao {
    @Query("SELECT * FROM recentViewedProducts")
    fun load(): List<RecentViewedProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(item: RecentViewedProductEntity)

    @Query("SELECT COUNT(*) FROM recentViewedProducts")
    fun count(): Int

    @Query(
        "DELETE FROM recentViewedProducts WHERE productId IN (SELECT productId FROM recentViewedProducts ORDER BY viewedAt ASC LIMIT :count)",
    )
    fun deleteOldest(count: Int)
}
