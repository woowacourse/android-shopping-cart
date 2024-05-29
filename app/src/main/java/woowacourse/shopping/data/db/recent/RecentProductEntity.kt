package woowacourse.shopping.data.db.recent

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.domain.model.RecentProduct
import java.time.LocalDateTime

@Entity(tableName = "recent_products")
data class RecentProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val productId: Long,
    val productName: String,
    val imageUrl: String,
    val dateTime: String,
)

fun RecentProductEntity.toRecentProduct(): RecentProduct {
    return RecentProduct(
        productId = this.productId,
        productName = this.productName,
        imageUrl = this.imageUrl,
        dateTime = LocalDateTime.parse(dateTime),
    )
}
