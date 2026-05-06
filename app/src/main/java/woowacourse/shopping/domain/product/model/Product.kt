package woowacourse.shopping.domain.product.model

import java.util.UUID

data class Product(
    val id: String = UUID.randomUUID().toString(),
    val imageUrl: ImageUrl,
    val name: ProductName,
    val price: Price,
)
