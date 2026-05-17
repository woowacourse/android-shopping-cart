package woowacourse.shopping.data.source.local.recent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recentProduct")
data class RecentProductEntity(
    @PrimaryKey @ColumnInfo(name = "product_id") val productId: String,
    @ColumnInfo(name = "view_time") val viewTime: Long,
)
