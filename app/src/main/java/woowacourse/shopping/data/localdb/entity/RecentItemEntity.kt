package woowacourse.shopping.data.localdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_items")
data class RecentItemEntity(
    @PrimaryKey val id: String,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val timestamp: Long,
)
