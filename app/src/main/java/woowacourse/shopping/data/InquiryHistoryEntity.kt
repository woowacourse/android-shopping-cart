package woowacourse.shopping.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.model.InquiryHistory
import java.time.LocalDateTime

@Entity(tableName = "inquiry_history")
data class InquiryHistoryEntity(
    @ColumnInfo(name = "product_id") val productId: Long,
    @ColumnInfo(name = "inquiry_time") val inquiryTime: LocalDateTime,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0L

    companion object {
        fun InquiryHistoryEntity.toDomainModel() =
            InquiryHistory(
                productId = productId,
                inquiryTime = inquiryTime,
            )
    }
}
