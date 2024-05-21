package woowacourse.shopping.domain.model

data class CartItem(
    val id: Long,
    val productId: Long,
    val productName: String,
    val price: Long,
    val imgUrl: String,
    val quantity: Int,
)
