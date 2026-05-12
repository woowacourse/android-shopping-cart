package woowacourse.shopping.data.source.local.recent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface RecentProductDao {
    @Query("SELECT * FROM recent_products ORDER BY lastViewedAt DESC LIMIT :limit")
    suspend fun getRecent(limit: Int = 10): List<RecentProductEntity>

    @Transaction
    suspend fun upsertAndTrim(
        entity: RecentProductEntity,
        keep: Int,
    ) {
        upsert(entity)
        trimToLast(keep)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: RecentProductEntity)

    @Query(
        """                                                                                                                                                                                                     
      DELETE FROM recent_products
      WHERE productId NOT IN (                                                                                                                                                                                   
          SELECT productId FROM recent_products                                                                                                                                                                
          ORDER BY lastViewedAt DESC LIMIT :keep
      )
  """,
    )
    suspend fun trimToLast(keep: Int)
}
