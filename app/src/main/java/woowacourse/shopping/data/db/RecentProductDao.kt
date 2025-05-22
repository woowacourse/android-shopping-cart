package woowacourse.shopping.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecentProductDao {
    @Query(
        """
        SELECT * FROM RecentProductEntity
        WHERE id IN (
            SELECT MAX(id)
            FROM RecentProductEntity
            GROUP BY productId
        )
        ORDER BY viewedAt DESC
        LIMIT :limit
    """,
    )
    fun getLatestUniqueRecentProducts(limit: Int): List<RecentProductEntity>

    @Insert
    fun insert(entity: RecentProductEntity)
}
