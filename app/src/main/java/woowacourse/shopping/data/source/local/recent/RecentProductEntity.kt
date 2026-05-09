package woowacourse.shopping.data.source.local.recent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_products")
data class RecentProductEntity(
    @PrimaryKey val productId: Long,
    val lastViewedAt: Long,
)
