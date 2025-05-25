package woowacourse.shopping.data.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history WHERE id =:id")
    fun findById(id: Long): HistoryEntity?

    @Query("SELECT * FROM history ORDER BY createdAt DESC LIMIT 1 OFFSET 1")
    fun findLast(): HistoryEntity?

    @Query("SELECT * FROM history ORDER BY createdAt DESC LIMIT 10")
    fun findRecentTen(): List<HistoryEntity>

    @Insert
    fun insert(productEntity: HistoryEntity)

    @Insert
    fun insertAll(vararg productEntity: HistoryEntity)

    @Query("DELETE FROM history WHERE id = :id")
    fun deleteById(id: Long)
}
