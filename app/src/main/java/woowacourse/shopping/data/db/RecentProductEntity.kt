package woowacourse.shopping.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["productId"], unique = true)],
    tableName = "RecentViewedProducts",
)
data class RecentProductEntity(
    @PrimaryKey val productId: Long,
    val lastViewedAt: Long = System.currentTimeMillis(),
)
