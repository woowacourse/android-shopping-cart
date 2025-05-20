package woowacourse.shopping.domain

data class Cart(
    val id: Long,
    val productId: Long,
    val quantity: Quantity,
)
