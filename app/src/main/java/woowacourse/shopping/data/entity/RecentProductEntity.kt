package woowacourse.shopping.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_product")
data class RecentProductEntity(
    @PrimaryKey @ColumnInfo(name = "product_id") val productId: Long,
    @ColumnInfo(name = "viewed_at") val viewedAt: Long = System.currentTimeMillis(),
)
