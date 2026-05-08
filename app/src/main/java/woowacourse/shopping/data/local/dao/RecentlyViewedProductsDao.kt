package woowacourse.shopping.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.data.local.entity.RecentViewedProductsEntity

@Dao
interface RecentlyViewedProductsDao {
    @Upsert
    suspend fun upsert(recentViewedProduct: RecentViewedProductsEntity)

    @Query(
        """
        SELECT * FROM recent_viewed_products
        ORDER BY viewedAt DESC
        LIMIT 10
        """,
    )
    fun getRecentViewedProducts(): Flow<List<RecentViewedProductsEntity>>

    @Query(
        """
        SELECT * FROM recent_viewed_products
        ORDER BY viewedAt DESC
        LIMIT 1
        """,
    )
    suspend fun getLastViewedProduct(): RecentViewedProductsEntity?

    @Query(
        """
        DELETE FROM recent_viewed_products
        WHERE productId NOT IN (
            SELECT productId
            FROM recent_viewed_products
            ORDER BY viewedAt DESC
            LIMIT 10
        )
        """,
    )
    suspend fun deleteExceptLatest10()
}
