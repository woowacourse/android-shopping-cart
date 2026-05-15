package woowacourse.shopping.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_product_table")
data class RecentProductEntity(
    @PrimaryKey val productId: String,
    val timestamp: Long = System.currentTimeMillis(),
)
