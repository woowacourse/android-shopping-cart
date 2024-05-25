package woowacourse.shopping.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.db.model.HistoryEntity

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putHistory(historyEntity: HistoryEntity)

    @Query("select * from HistoryEntity order by timeStamp desc limit :size")
    fun getHistories(size: Int): List<HistoryEntity>
}
