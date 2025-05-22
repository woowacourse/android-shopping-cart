package woowacourse.shopping.domain

data class ShoppingCartItem(
    val id: Long,
    val product: Product,
    val quantity: Int,
)
