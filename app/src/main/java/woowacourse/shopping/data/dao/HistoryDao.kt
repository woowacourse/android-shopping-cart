package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import woowacourse.shopping.data.dto.HistoryProductDto
import woowacourse.shopping.data.entity.HistoryProductEntity

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: HistoryProductEntity)

    @Transaction
    fun insertHistoryWithLimit(
        history: HistoryProductEntity,
        limit: Int,
    ) {
        insertHistory(history)

        val historyCount = getHistoryCount()
        if (historyCount > limit) {
            deleteOldestHistories(historyCount - limit)
        }
    }

    @Query("SELECT * FROM search_history ORDER BY timestamp DESC")
    fun getHistoryProducts(): List<HistoryProductDto>

    @Query("SELECT COUNT(*) FROM search_history")
    fun getHistoryCount(): Int

    @Query("DELETE FROM search_history WHERE productId = (SELECT productId FROM search_history ORDER BY timestamp ASC LIMIT :count)")
    fun deleteOldestHistories(count: Int)

    @Query("SELECT * FROM search_history ORDER BY timestamp DESC LIMIT 1")
    fun getRecentHistoryProduct(): HistoryProductDto?
}
