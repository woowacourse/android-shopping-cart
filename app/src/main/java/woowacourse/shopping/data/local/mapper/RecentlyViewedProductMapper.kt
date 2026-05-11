package woowacourse.shopping.data.local.mapper

import woowacourse.shopping.data.local.entity.RecentlyViewedProductEntity
import woowacourse.shopping.domain.model.product.Price
import woowacourse.shopping.domain.model.product.Product

fun RecentlyViewedProductEntity.toDomain(): Product =
    Product(
        productId = productId,
        productName = productName,
        price = Price(price),
        imageUrl = imageUrl,
    )
