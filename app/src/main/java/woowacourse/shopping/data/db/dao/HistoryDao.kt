package woowacourse.shopping.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.db.entity.HistoryEntity

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(history: HistoryEntity)

    @Query("SELECT * FROM history_table ORDER BY createdAt DESC LIMIT 10")
    fun getLatestHistory(): List<HistoryEntity>
}
