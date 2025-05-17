package woowacourse.shopping.domain

data class CartResult(
    val products: List<Product>,
    val hasNextPage: Boolean,
)
