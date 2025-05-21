package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.dto.ProductDto
import woowacourse.shopping.data.entity.ProductEntity
import woowacourse.shopping.domain.model.CatalogProduct
import woowacourse.shopping.domain.model.Product

fun ProductEntity.toDomain(): Product =
    Product(
        id = id,
        name = name,
        imageUrl = imageUrl,
        price = price,
    )

fun ProductDto.toDomain(): CatalogProduct =
    CatalogProduct(
        product =
            Product(
                id = id,
                name = name,
                imageUrl = imageUrl,
                price = price,
            ),
        quantity = cartQuantity,
    )
