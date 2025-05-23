package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.recent.RecentProductEntity
import woowacourse.shopping.domain.model.RecentProduct

fun RecentProductEntity.toDomain() =
    RecentProduct(
        id = this.id,
        product = this.productId.toProduct(),
    )

fun RecentProduct.toEntity() =
    RecentProductEntity(
        productId = this.product.toId(),
    )
