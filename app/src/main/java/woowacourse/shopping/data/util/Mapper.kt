package woowacourse.shopping.data.util

import woowacourse.shopping.data.local.CartEntity
import woowacourse.shopping.data.remote.dto.ProductDto
import woowacourse.shopping.data.remote.dto.ProductsResponseDto
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.CartItems
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products

fun CartEntity.toDomain(): CartItem =
    CartItem(
        productId = productId,
        amount = amount,
    )

fun List<CartEntity>.toDomain(isLast: Boolean): CartItems =
    CartItems(
        items = map { it.toDomain() },
        isLast = isLast,
    )

fun ProductDto.toDomain(): Product =
    Product(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
    )

fun ProductsResponseDto.toDomain(): Products =
    Products(
        items = products.map { it.toDomain() },
        hasNext = !last,
    )
