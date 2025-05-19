package woowacourse.shopping.domain.cart

data class CartResult(
    val carts: List<Cart>,
    val hasNextPage: Boolean,
)
