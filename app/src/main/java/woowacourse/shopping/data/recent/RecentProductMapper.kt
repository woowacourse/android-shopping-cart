package woowacourse.shopping.data.recent

import woowacourse.shopping.data.product.toId
import woowacourse.shopping.data.product.toProduct
import woowacourse.shopping.domain.model.RecentProduct
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun RecentProductEntity.toDomain() =
    RecentProduct(
        product = this.productId.toProduct(),
        viewedAt =
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(viewedAt),
                ZoneId.systemDefault(),
            ),
    )

fun RecentProduct.toEntity() =
    RecentProductEntity(
        productId = this.product.toId(),
        viewedAt = viewedAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
    )
