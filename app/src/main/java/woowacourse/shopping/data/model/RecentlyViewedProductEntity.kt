package woowacourse.shopping.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recently_viewed_products")
data class RecentlyViewedProductEntity(
    @PrimaryKey val productId: Long,
    val name: String,
    val price: Long,
    val imageUrl: String,
    val viewedAt: Long,
)
