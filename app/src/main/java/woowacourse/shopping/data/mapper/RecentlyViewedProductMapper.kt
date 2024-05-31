package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.RecentlyViewedProductEntity
import woowacourse.shopping.domain.model.RecentlyViewedProduct

fun RecentlyViewedProductEntity.toDomainModel(): RecentlyViewedProduct {
    return RecentlyViewedProduct(
        productId = this.productId,
        name = this.name,
        price = this.price,
        imageUrl = this.imageUrl,
        viewedAt = this.viewedAt,
    )
}

fun RecentlyViewedProduct.toEntityModel(): RecentlyViewedProductEntity {
    return RecentlyViewedProductEntity(
        productId = this.productId,
        name = this.name,
        price = this.price,
        imageUrl = this.imageUrl,
        viewedAt = this.viewedAt,
    )
}
