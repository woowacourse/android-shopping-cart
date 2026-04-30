package woowacourse.shopping.domain.model.product

import woowacourse.shopping.domain.model.Price
import java.util.UUID

data class Product(
    val id: String = UUID.randomUUID().toString(),
    val imageUrl: String,
    val productTitle: ProductTitle,
    val price: Price,
)
