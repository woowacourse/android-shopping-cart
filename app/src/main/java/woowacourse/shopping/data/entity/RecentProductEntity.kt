package woowacourse.shopping.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "recent_product")
data class RecentProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @Embedded("product_")
    val product: ProductEntity,
    @ColumnInfo(name = "look_date_time") val lookDateTime: LocalDateTime,
)
