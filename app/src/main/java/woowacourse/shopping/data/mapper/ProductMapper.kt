package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.remote.dto.ProductResponseDto
import woowacourse.shopping.domain.product.ImageUrl
import woowacourse.shopping.domain.product.Price
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductName

fun ProductResponseDto.toDomain(): Product =
    Product(
        id = id,
        imageUrl = ImageUrl(imageUrl),
        name = ProductName(name),
        price = Price(price),
    )
