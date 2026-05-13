package woowacourse.shopping.data.remote.mapper

import woowacourse.shopping.data.remote.dto.ProductResponse
import woowacourse.shopping.domain.model.product.Price
import woowacourse.shopping.domain.model.product.Product

fun ProductResponse.toDomain(): Product =
    Product(
        productId = id,
        productName = name,
        price = Price(price),
        imageUrl = imageUrl,
    )

fun Product.toResponse(): ProductResponse =
    ProductResponse(
        id = productId,
        name = productName,
        price = price.value,
        imageUrl = imageUrl,
    )
