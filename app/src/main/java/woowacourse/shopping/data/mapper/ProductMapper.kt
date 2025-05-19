package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.entity.ProductEntity
import woowacourse.shopping.domain.model.Product

fun ProductEntity.toDomain(): Product =
    Product(
        id = id,
        name = name,
        imageUrl = imageUrl,
        price = price,
    )
