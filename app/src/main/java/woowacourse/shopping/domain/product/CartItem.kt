package woowacourse.shopping.domain.product

data class CartItem(
    val id: Long,
    val product: Product,
    val quantity: Int,
)
