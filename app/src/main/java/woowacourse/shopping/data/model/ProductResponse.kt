package woowacourse.shopping.data.model

import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product

data class ProductResponse(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val price: Int,
)

fun ProductResponse.toProduct() =
    Product(
        id = id,
        name = name,
        imageUrl = imageUrl,
        price = Price(price),
    )
