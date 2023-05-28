package woowacourse.shopping.data.model

data class CartEntity(
    val id: Long,
    val productId: Long,
    val count: Int,
    val checked: Int
)
