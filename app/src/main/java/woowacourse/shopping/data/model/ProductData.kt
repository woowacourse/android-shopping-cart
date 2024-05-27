package woowacourse.shopping.data.model

import woowacourse.shopping.domain.model.Product

data class ProductData(
    val id: Long,
    val imgUrl: String,
    val name: String,
    val price: Int,
) {
    companion object {
        val NULL =
            ProductData(
                id = -1,
                imgUrl = "0",
                name = "상품이 없습니다.",
                price = 0,
            )
    }
}

fun ProductData.toDomain(quantity: Int = 0): Product =
    Product(
        id,
        imgUrl,
        name,
        price,
        quantity = quantity,
    )
