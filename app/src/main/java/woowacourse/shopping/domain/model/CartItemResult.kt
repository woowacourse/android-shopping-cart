package woowacourse.shopping.domain.model

data class CartItemResult(
    val cartItemId: Long,
    val counter: CartItemCounter,
)
