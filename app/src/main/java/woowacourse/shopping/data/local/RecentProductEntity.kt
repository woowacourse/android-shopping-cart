package woowacourse.shopping.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecentProductEntity(
    @PrimaryKey
    @ColumnInfo(name = "product_id")
    val productId: String,
    @ColumnInfo(name = "viewed_at")
    val viewedAt: Long,
)
