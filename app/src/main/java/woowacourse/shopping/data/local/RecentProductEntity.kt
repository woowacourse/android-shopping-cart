package woowacourse.shopping.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_products")
data class RecentProductEntity(
    @PrimaryKey val id: String,
    val imageUrl: String,
    val name: String,
    val timestamp: Long
)
