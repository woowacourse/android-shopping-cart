package woowacourse.shopping.domain.entity

data class Product(
    val id: Long,
    val price: Int,
    val name: String,
    val imageUrl: String,
)
