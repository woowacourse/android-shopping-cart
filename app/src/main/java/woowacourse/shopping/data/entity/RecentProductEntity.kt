package woowacourse.shopping.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "recent_product")
data class RecentProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("product_id") val productId: Long,
    val viewTime: LocalDateTime,
)
