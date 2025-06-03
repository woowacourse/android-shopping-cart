package woowacourse.shopping.data.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history ORDER BY timestamp DESC")
    fun getAll(): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg historyEntity: HistoryEntity)

    @Query("SELECT * FROM history WHERE id = :id LIMIT 1")
    fun findById(id: Long): HistoryEntity?

    @Query("DELETE FROM history WHERE id = (SELECT id FROM history ORDER BY timestamp ASC LIMIT 1)")
    fun deleteOldest()

    @Query("SELECT * FROM history ORDER BY timestamp DESC LIMIT 1")
    fun findLatest(): HistoryEntity?
}
