package woowacourse.shopping.data.local.recentproduct

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentProductDao {

    @Query("SELECT productId FROM recent_products ORDER BY viewedAt DESC LIMIT :limit")
    suspend fun findAll(limit: Int): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: RecentProductEntity)

    @Query(
        """
        DELETE FROM recent_products
        WHERE productId NOT IN (
            SELECT productId FROM recent_products
            ORDER BY viewedAt DESC
            LIMIT :limit
        )
        """,
    )
    suspend fun trim(limit: Int)
}
