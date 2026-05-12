package woowacourse.shopping.data.local.recent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_products")
data class RecentProductEntity(
    @PrimaryKey val productId: Int,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val viewedAt: Long,
)
