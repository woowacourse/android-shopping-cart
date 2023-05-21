package woowacourse.shopping.database.product

data class ProductEntity(
    val id: Int,
    val name: String,
    val price: Int,
    val imageUrl: String,
)
