package woowacourse.shopping.data.model

import woowacourse.shopping.domain.model.Product

data class ProductData(
    val id: Int,
    val imgUrl: String,
    val name: String,
    val price: Int,
)

fun ProductData.toDomain(quantity: Int = 0): Product =
    Product(
        id,
        imgUrl,
        name,
        price,
        quantity = quantity,
    )
