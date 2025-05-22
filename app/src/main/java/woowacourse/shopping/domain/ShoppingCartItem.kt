package woowacourse.shopping.domain

data class ShoppingCartItem(
    val id: Long = 0,
    val product: Product,
    val quantity: Int,
)
