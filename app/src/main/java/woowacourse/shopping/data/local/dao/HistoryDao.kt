package woowacourse.shopping.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.shopping.data.local.entity.HistoryEntity

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history WHERE id =:id")
    fun findById(id: Long): HistoryEntity?

    @Query("SELECT * FROM history ORDER BY createdAt DESC LIMIT :limit OFFSET :offset")
    fun findMostRecentProduct(
        limit: Int = 1,
        offset: Int = 1,
    ): HistoryEntity?

    @Query("SELECT * FROM history ORDER BY createdAt DESC LIMIT :limit")
    fun findRecentProduct(limit: Int): List<HistoryEntity>

    @Insert
    fun insert(historyEntity: HistoryEntity)

    @Insert
    fun insertAll(vararg historyEntity: HistoryEntity)

    @Query("DELETE FROM history WHERE id = :id")
    fun deleteById(id: Long)
}
