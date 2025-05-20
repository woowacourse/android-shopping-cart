package woowacourse.shopping.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class ExploreHistoryProductEntity(
    @PrimaryKey val productId: Int,
    val timestamp: Long = System.currentTimeMillis(),
)
