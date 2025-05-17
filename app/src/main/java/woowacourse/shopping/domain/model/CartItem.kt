package woowacourse.shopping.domain.model

data class CartItem(
    val id: Long,
    val productId: Long,
    val quantity: Int,
)
