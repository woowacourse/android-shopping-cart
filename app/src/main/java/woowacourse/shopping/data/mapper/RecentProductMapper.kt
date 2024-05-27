package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.RecentProductEntity
import woowacourse.shopping.domain.model.RecentProduct

fun RecentProductEntity.toDomainModel(): RecentProduct {
    return RecentProduct(
        id = this.id,
        productId = this.productId,
        productName = this.productName,
        imageUrl = this.imgUrl,
    )
}
