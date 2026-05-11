package woowacourse.shopping.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "recent_products")
data class RecentProductEntity(
    @PrimaryKey val productId: UUID,
    val viewedAt: Long = System.currentTimeMillis(),
)
