package woowacourse.shopping.data.recentvieweditem

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.data.recentvieweditem.RecentViewedItemDatabase.Companion.RECENT_VIEWED_ITEM_DB_NAME
import woowacourse.shopping.domain.model.Product
import java.time.LocalDateTime

@Entity(tableName = RECENT_VIEWED_ITEM_DB_NAME)
data class RecentViewedItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val productId: Long,
    val product: Product,
    val viewedAt: LocalDateTime,
) {
    companion object {
        fun makeRecentViewedItemEntity(product: Product): RecentViewedItemEntity {
            return RecentViewedItemEntity(
                productId = product.id,
                product = product,
                viewedAt = LocalDateTime.now(),
            )
        }
    }
}
