package woowacourse.shopping.data.product.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.domain.product.Product

@Entity(tableName = "recent_watching")
data class RecentWatchingEntity(
    @PrimaryKey val productId: Long,
    @Embedded val product: Product,
    val watchedAt: Long = System.currentTimeMillis(),
)
