package woowacourse.shopping.domain.product

data class ProductResult(
    val products: List<Product>,
    val hasNextPage: Boolean,
)
