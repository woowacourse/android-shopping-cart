package woowacourse.shopping.local.recent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(recentProductEntity: RecentProductEntity)

    @Query(
        """
        SELECT * FROM recent_products
        ORDER BY viewedAtMillis DESC, productId ASC
        LIMIT :limit
        """,
    )
    suspend fun getRecentProducts(limit: Int): List<RecentProductEntity>

    @Query(
        """
        SELECT * FROM recent_products
        WHERE productId != :productId
        ORDER BY viewedAtMillis DESC, productId ASC
        LIMIT 1
        """,
    )
    suspend fun getLatestViewedProductExcluding(productId: String): RecentProductEntity?

    @Query(
        """
        DELETE FROM recent_products
        WHERE productId NOT IN (
            SELECT productId
            FROM recent_products
            ORDER BY viewedAtMillis DESC, productId ASC
            LIMIT :limit
        )
        """,
    )
    suspend fun trimTo(limit: Int)
}
