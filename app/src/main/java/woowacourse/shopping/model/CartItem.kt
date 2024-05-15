package woowacourse.shopping.model

data class CartItem(
    val id: Long,
    val productId: Long,
    val quantity: Quantity,
)
