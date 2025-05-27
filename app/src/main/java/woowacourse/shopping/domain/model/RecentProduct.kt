package woowacourse.shopping.domain.model

import woowacourse.shopping.data.entity.RecentProductEntity
import java.time.LocalDateTime
import java.time.ZoneId

data class RecentProduct(
    val product: Product,
    val viewedAt: LocalDateTime = LocalDateTime.now(),
)

fun RecentProduct.toEntity() =
    RecentProductEntity(
        productId = this.product.id,
        viewedAt = viewedAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
    )
