package woowacourse.shopping.data.local.mapper

import woowacourse.shopping.data.local.entity.RecentProductEntity
import woowacourse.shopping.domain.RecentProduct

fun RecentProductEntity.toDomain(): RecentProduct {
    return RecentProduct(
        productId,
        name,
        imgUrl,
        price,
        createdAt,
    )
}
