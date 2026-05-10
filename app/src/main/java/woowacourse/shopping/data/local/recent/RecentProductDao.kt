package woowacourse.shopping.data.local.recent

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentProductDao {
    @Query(
        """
        SELECT * FROM recent_products
        ORDER BY viewedAt DESC
        LIMIT :limit
        """,
    )
    fun getRecentProducts(limit: Int): Flow<List<RecentProductEntity>>

    @Query(
        """
        SELECT * FROM recent_products
        ORDER BY viewedAt DESC
        LIMIT 1
        """,
    )
    suspend fun getMostRecentProduct(): RecentProductEntity?

    @Upsert
    suspend fun upsert(recentProduct: RecentProductEntity)

    @Query(
        """
        DELETE FROM recent_products
        WHERE productId IN (
            SELECT productId
            FROM recent_products
            ORDER BY viewedAt DESC
            LIMIT -1 OFFSET :limit
        )
        """,
    )
    suspend fun deleteOlderThan(limit: Int)
}
