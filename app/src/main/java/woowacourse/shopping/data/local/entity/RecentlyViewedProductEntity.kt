package woowacourse.shopping.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recently_viewed_products")
class RecentlyViewedProductEntity(
    @PrimaryKey val productId: String,
    val viewedAt: Long,
)
