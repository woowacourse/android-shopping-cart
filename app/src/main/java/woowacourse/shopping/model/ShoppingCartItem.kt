package woowacourse.shopping.model

data class ShoppingCartItem(
    val id: String,
    val quantity: Quantity,
    val product: Product,
)
