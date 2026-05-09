package woowacourse.shopping.data.remote.mapper

import woowacourse.shopping.data.local.entity.ProductEntity
import woowacourse.shopping.data.remote.dto.ProductResponse
import woowacourse.shopping.domain.Price

fun ProductResponse.toEntity(): ProductEntity =
    ProductEntity(
        productId = id,
        productName = name,
        imageUrl = imageUrl,
        price = Price(price),
    )
