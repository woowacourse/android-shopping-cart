package woowacourse.shopping.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.local.entity.RecentlyViewedProductEntity

@Dao
interface RecentlyViewedProductDao {
    @Query(
        """
            SELECT * FROM recently_viewed_products
            ORDER BY viewedAt DESC
            LIMIT 10
        """,
    )
    suspend fun findAll(): List<RecentlyViewedProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entity: RecentlyViewedProductEntity)

    @Query(
        """
            DELETE FROM recently_viewed_products
            WHERE productId NOT IN (
                SELECT productId 
                FROM recently_viewed_products
                ORDER BY viewedAt DESC
                LIMIT 10
            )
        """,
    )
    suspend fun deleteOverLimit()

    @Query(
        """
            SELECT * FROM recently_viewed_products ORDER BY 
            viewedAt DESC LIMIT 1
        """,
    )
    suspend fun findLastViewedProduct(): RecentlyViewedProductEntity?
}
