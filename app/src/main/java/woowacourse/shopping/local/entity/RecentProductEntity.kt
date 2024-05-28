package woowacourse.shopping.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "RecentProduct")
data class RecentProductEntity(
    @PrimaryKey val productId: Long,
    val createdTime: LocalDateTime,
)
