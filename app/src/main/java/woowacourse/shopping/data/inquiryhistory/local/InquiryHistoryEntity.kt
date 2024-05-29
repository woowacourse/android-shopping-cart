package woowacourse.shopping.data.inquiryhistory.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.model.InquiryHistory
import woowacourse.shopping.model.Product
import java.time.LocalDateTime

@Entity(tableName = "inquiry_history")
data class InquiryHistoryEntity(
    @ColumnInfo(name = "product") val product: Product,
    @ColumnInfo(name = "inquiry_time") val inquiryTime: LocalDateTime,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0L

    companion object {
        fun InquiryHistoryEntity.toDomainModel() =
            InquiryHistory(
                product = product,
                inquiryTime = inquiryTime,
            )
    }
}
