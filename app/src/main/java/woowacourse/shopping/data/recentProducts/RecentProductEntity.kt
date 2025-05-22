package woowacourse.shopping.data.recentProducts

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_product")
data class RecentProductEntity(
    @PrimaryKey val id: Long,
    val viewedTime: Long = System.currentTimeMillis(),
)
