package woowacourse.shopping.data.shoppingCart

data class ProductInCartEntity(
    val id: Long,
    val productId: Long,
    val quantity: Int,
)
