package woowacourse.shopping.domain

data class Product(
    val id: Int,
    val picture: URL,
    val title: String,
    val price: Int
)
