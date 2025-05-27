package woowacourse.shopping.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "recent_product")
data class RecentProductEntity(
    @PrimaryKey val productId: Long,
    val createdDateTime: LocalDateTime,
)
