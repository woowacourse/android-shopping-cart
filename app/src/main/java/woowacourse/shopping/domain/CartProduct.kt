package woowacourse.shopping.domain

data class CartProduct(
    val id: Int,
    val name: String,
    val price: Int,
    val quantity: Int,
    val imageUrl: String,
)
