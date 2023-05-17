package woowacourse.shopping.data.model

data class CartEntity(
    val productId: Long,
    val count: Int,
    val isSelected: Int
)
