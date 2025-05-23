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
}
