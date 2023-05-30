package woowacourse.shopping.data.model

data class CartProductEntity(
    val productId: Long,
    val count: Int,
    val isSelected: Boolean
)
