package woowacourse.shopping.data.recent

import woowacourse.shopping.domain.model.RecentProduct
import java.time.ZoneId

fun RecentProduct.toEntity() =
    RecentProductEntity(
        productId = this.product.id,
        viewedAt = viewedAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
    )
