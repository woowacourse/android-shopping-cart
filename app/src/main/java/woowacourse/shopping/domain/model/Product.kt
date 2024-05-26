package woowacourse.shopping.domain.model

import woowacourse.shopping.data.model.ProductData

data class Product(
    val id: Long,
    val imgUrl: String,
    val name: String,
    val price: Int,
    val quantity: Int,
) {
    companion object {
        val NULL =
            Product(
                id = -1,
                imgUrl = "0",
                name = "상품이 없습니다.",
                price = 0,
                quantity = 0,
            )
    }
}

fun Product.toData(): ProductData =
    ProductData(
        id = id,
        imgUrl = imgUrl,
        name = name,
        price = price,
    )
