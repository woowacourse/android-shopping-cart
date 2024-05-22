package woowacourse.shopping.model

data class Product(
    val id: Long = 0,
    val imageUrl: String,
    val name: String,
    val price: Int,
)
