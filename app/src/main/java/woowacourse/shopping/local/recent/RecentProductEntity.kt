package woowacourse.shopping.local.recent

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.model.ProductId
import woowacourse.shopping.model.RecentProduct
import java.util.UUID

@Entity(tableName = "recent_products")
data class RecentProductEntity(
    @PrimaryKey val productId: String,
    val viewedAtMillis: Long,
) {
    fun toDomain(): RecentProduct =
        RecentProduct(
            productId = ProductId(UUID.fromString(productId)),
            viewedAtMillis = viewedAtMillis,
        )

    companion object {
        fun fromDomain(recentProduct: RecentProduct): RecentProductEntity =
            RecentProductEntity(
                productId = recentProduct.productId.value.toString(),
                viewedAtMillis = recentProduct.viewedAtMillis,
            )
    }
}
