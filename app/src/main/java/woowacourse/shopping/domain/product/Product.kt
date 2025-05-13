package woowacourse.shopping.domain.product

data class Product(
    val imageUrl: String,
    val name: String,
    private val _price: Money,
) {
    val price: Int get() = _price.amount
}
