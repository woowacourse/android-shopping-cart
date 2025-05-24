package woowacourse.shopping.domain.cart

data class CartSinglePage(
    val carts: List<Cart>,
    val hasNextPage: Boolean,
)
