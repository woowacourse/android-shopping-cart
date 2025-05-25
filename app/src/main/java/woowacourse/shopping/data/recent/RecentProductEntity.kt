package woowacourse.shopping.data.recent

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.data.recent.RecentProductEntity.Companion.RECENT_PRODUCT_TABLE_NAME

@Entity(tableName = RECENT_PRODUCT_TABLE_NAME)
data class RecentProductEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
    val timestamp: Long,
) {
    companion object {
        const val RECENT_PRODUCT_TABLE_NAME = "recent_product"
    }
}
