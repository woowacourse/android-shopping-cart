package woowacourse.shopping.domain.model

data class Product(
    val id: Long = 0L,
    val name: String,
    val price: Long,
    val imageUrl: String,
)
