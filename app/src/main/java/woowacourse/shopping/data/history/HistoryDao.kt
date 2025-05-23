package woowacourse.shopping.data.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history")
    fun getAll(): List<HistoryEntity>

    @Insert
    fun insert(vararg historyEntity: HistoryEntity)

    @Query("SELECT * FROM history WHERE id = :id LIMIT 1")
    fun findById(id: Long): HistoryEntity?

    @Query("DELETE FROM history WHERE id = (SELECT id FROM history ORDER BY id ASC LIMIT 1)")
    fun deleteOldest()
}
