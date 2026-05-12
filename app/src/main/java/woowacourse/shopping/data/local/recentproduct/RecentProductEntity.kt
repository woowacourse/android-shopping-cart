package woowacourse.shopping.data.local.recentproduct

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_products")
data class RecentProductEntity(
    @PrimaryKey
    val productId: String,
    val viewedAt: Long,
)
