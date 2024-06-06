package woowacourse.shopping.data.recent.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "recent_products")
class RecentProduct(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    @ColumnInfo(name = "product_id") val productId: Long,
    @ColumnInfo(name = "seen_date_time") val seenDateTime: LocalDateTime,
)
