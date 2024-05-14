package woowacourse.shopping.domain

data class Product(
    val id: Long,
    val imageUrl: ImageUrl,
    val name: String,
    val price: Price,
)
