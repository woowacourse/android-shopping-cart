package woowacourse.shopping.domain.model

data class CartItem(
    val id: Long,
    val product: Product,
    val cartItemCounter: CartItemCounter,
)

