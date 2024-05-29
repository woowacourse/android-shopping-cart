package woowacourse.shopping.data.inquiryhistory.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface InquiryHistoryDao {
    @Query("SELECT * FROM inquiry_history")
    fun getAll(): List<InquiryHistoryEntity>

    @Insert
    fun insert(inquiryHistoryEntity: InquiryHistoryEntity)

    @Delete
    fun delete(inquiryHistoryEntity: InquiryHistoryEntity)
}
