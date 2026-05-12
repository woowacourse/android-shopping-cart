package woowacourse.shopping.data.localdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.data.localdb.entity.RecentItemEntity

@Dao
interface RecentItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: RecentItemEntity)

    @Query("SELECT * FROM recent_items ORDER BY timestamp DESC, id DESC LIMIT 10")
    fun getRecentItems(): Flow<List<RecentItemEntity>>

    @Query("SELECT * FROM recent_items WHERE id = :id")
    suspend fun getRecentItemById(id: String): RecentItemEntity?

    @Query(
        """
        DELETE FROM recent_items
        WHERE id NOT IN (SELECT id FROM recent_items ORDER BY timestamp DESC, id DESC LIMIT 10)
    """,
    )
    suspend fun deleteOldItem()

    @Query(
        """
            SELECT * FROM recent_items
            ORDER BY timestamp DESC, id DESC
            LIMIT 1
        """,
    )
    suspend fun getLastViewedItem(): RecentItemEntity?
}
