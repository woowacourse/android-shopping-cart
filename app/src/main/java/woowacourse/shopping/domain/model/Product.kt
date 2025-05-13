package woowacourse.shopping.domain.model

data class Product(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val price: Price,
)
