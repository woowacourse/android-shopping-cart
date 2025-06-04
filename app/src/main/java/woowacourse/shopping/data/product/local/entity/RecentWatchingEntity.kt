package woowacourse.shopping.data.product.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_watching")
data class RecentWatchingEntity(
    @PrimaryKey val productId: Long,
    val watchedAt: Long = System.currentTimeMillis(),
)
