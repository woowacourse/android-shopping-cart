package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.ProductEntity
import woowacourse.shopping.domain.model.Product

fun Product.toProductEntity() =
    ProductEntity(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
    )
