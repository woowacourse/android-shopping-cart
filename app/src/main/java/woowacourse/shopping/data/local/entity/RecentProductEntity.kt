package woowacourse.shopping.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_products")
data class RecentProductEntity(
    @PrimaryKey val productId: String,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val viewedAt: Long = System.currentTimeMillis(),
)
