package woowacourse.shopping.domain.cart

data class CartResult(
    val products: List<Cart>,
    val hasNextPage: Boolean,
)
