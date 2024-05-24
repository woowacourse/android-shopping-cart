package woowacourse.shopping.domain.model

import woowacourse.shopping.data.model.ProductData

data class Product(
    val id: Int,
    val imgUrl: String,
    val name: String,
    val price: Int,
    val quantity: Int,
)

fun Product.toData(): ProductData = ProductData(
    id = id, imgUrl = imgUrl, name = name, price = price
)
