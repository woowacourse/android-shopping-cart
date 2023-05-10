package woowacourse.shopping.data.model

data class CartEntity(
    val cartId: Long,
    val productId: Long,
    val count: Int
)
