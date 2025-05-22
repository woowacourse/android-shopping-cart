package woowacourse.shopping.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "recentlyViewedProduct")
data class RecentlyViewedProduct(
    @PrimaryKey val productId: Long,
    val viewedAt: LocalDateTime,
)
