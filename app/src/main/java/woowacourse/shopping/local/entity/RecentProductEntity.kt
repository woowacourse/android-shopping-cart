package woowacourse.shopping.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "recent_products")
data class RecentProductEntity(
    @PrimaryKey val productId: UUID,
    val viewedAt: Long,
)
