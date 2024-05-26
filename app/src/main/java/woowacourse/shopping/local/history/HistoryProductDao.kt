package woowacourse.shopping.local.history

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import woowacourse.shopping.data.model.HistoryProduct

@Dao
interface HistoryProductDao {
    @Insert
    fun insert(historyProduct: HistoryProduct): Long

    @Query("SELECT * from history_products WHERE id = :id")
    fun findById(id: Long): HistoryProduct?

    @Query("SELECT * from history_products ORDER BY timestamp DESC LIMIT 1")
    fun findLatest(): HistoryProduct?

    @Query("SELECT * from history_products ORDER BY timestamp ASC")
    fun findAll(): List<HistoryProduct>

    @Delete
    fun delete(historyProduct: HistoryProduct)

    @Query("DELETE FROM history_products")
    fun deleteAll()
}
