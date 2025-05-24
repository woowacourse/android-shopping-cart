package woowacourse.shopping.domain.product

data class ProductSinglePage(
    val products: List<Product>,
    val hasNextPage: Boolean,
)
