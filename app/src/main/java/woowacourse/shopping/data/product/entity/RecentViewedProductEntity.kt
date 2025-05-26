package woowacourse.shopping.data.product.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "recentViewedProducts")
class RecentViewedProductEntity(
    @PrimaryKey
    val productId: Long,
    val viewedAt: LocalDateTime,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RecentViewedProductEntity

        return productId == other.productId
    }

    override fun hashCode(): Int = productId.hashCode()
}
