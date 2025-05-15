package woowacourse.shopping.domain

data class Product(
    val id: Long,
    val name: String,
    val price: Price,
    val imgUrl: String,
) {
    val priceValue: Int = price.value
}
