package woowacourse.shopping.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.data.db.recentviewedItem.RecentViewedItemDatabase.Companion.RECENT_VIEWED_ITEM_DB_NAME
import woowacourse.shopping.domain.model.Product
import java.time.LocalDateTime

@Entity(tableName = RECENT_VIEWED_ITEM_DB_NAME)
data class RecentViewedItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val product: Product,
    val viewedAt: LocalDateTime,
) {
    companion object {
        fun makeRecentViewedItemEntity(product: Product): RecentViewedItemEntity {
            return RecentViewedItemEntity(product = product, viewedAt = LocalDateTime.now())
        }
    }
}
