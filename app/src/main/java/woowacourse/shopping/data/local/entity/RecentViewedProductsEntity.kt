package woowacourse.shopping.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_viewed_products")
data class RecentViewedProductsEntity(
    @PrimaryKey
    val productId: String,
    val viewedAt: Long,
)
