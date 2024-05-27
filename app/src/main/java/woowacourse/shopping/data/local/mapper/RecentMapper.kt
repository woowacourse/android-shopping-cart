package woowacourse.shopping.data.local.mapper

import woowacourse.shopping.data.local.entity.RecentEntity
import woowacourse.shopping.domain.Recent

fun Recent.toEntity(): RecentEntity {
    return RecentEntity(
        productId,
        createdAt,
    )
}
