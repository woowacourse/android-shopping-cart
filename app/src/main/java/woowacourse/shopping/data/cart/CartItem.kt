package woowacourse.shopping.data.cart

data class CartItem(
    val id: Long,
    val quantity: Int,
    val productId: Long,
)
