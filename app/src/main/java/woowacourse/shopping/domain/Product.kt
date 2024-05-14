package woowacourse.shopping.domain

data class Product(
    val id: Long,
    val name: String,
    val imageSource: String,
    val price: Int,
)
