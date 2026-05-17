package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.local.recent.RecentProductEntity
import woowacourse.shopping.domain.product.ImageUrl
import woowacourse.shopping.domain.product.Price
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductName

fun RecentProductEntity.toDomain(): Product =
    Product(
        id = productId,
        imageUrl = ImageUrl(imageUrl),
        name = ProductName(name),
        price = Price(price),
    )

fun Product.toRecentProductEntity(viewedAt: Long): RecentProductEntity =
    RecentProductEntity(
        productId = id,
        name = name.value,
        price = price.value,
        imageUrl = imageUrl.value,
        viewedAt = viewedAt,
    )
