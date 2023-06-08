package woowacourse.shopping.data.entity

data class ProductInCartEntity(
    val productId: Long,
    val quantity: Int = 1,
)
