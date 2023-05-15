package woowacourse.shopping.model

data class CartProduct(
    val id: Int,
    val name: String,
    val count: Int,
    val price: Int,
    val imageUrl: String
)
