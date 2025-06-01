package woowacourse.shopping.data.recentProducts

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_product")
data class RecentProductEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val imageUrl: String,
    val price: Int,
    val quantity: Int,
    val viewedTime: Long = System.currentTimeMillis(),
)
