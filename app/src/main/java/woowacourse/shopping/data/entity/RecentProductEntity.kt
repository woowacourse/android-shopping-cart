package woowacourse.shopping.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "recent_product")
data class RecentProductEntity(
    @PrimaryKey
    @ColumnInfo("product_id") val productId: Long,
    @ColumnInfo(name = "look_date_time") val lookDateTime: LocalDateTime,
) {
    companion object {
        val STUB = RecentProductEntity(ProductEntity.STUB_LIST.first().id, LocalDateTime.now())
    }
}
