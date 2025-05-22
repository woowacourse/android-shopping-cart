package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.recent.RecentProductEntity
import woowacourse.shopping.domain.RecentProduct

fun List<RecentProductEntity>.toDomain() =
    this.map { entity ->
        RecentProduct(
            id = entity.id,
            product = entity.productId.toProduct(),
        )
    }

fun RecentProduct.toEntity() =
    RecentProductEntity(
        productId = this.product.toId(),
    )
