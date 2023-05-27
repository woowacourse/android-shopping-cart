package woowacourse.shopping.model

data class Product(
    val id: Long,
    val imageUrl: String,
    val name: String,
    val price: Price,
) {
    companion object {
        val defaultProduct = Product(
            id = 0,
            imageUrl = "",
            name = "상품을 불러올 수 없음",
            price = Price(0),
        )
    }
}
