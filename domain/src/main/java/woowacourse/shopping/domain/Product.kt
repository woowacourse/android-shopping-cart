package woowacourse.shopping.domain

data class Product(
    val id: Int,
    val name: String,
    val price: Price,
    val imageUrl: String,
    val selectedCount: Int = 0,
)
