package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.entity.ProductEntity
import woowacourse.shopping.data.model.response.ProductResponse
import woowacourse.shopping.domain.model.CatalogProduct
import woowacourse.shopping.domain.model.Product

fun ProductEntity.toDomain(): Product =
    Product(
        id = id,
        name = name,
        imageUrl = imageUrl,
        price = price,
    )

fun ProductResponse.toDomain(): CatalogProduct =
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
