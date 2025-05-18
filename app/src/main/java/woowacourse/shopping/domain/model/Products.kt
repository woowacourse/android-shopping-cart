package woowacourse.shopping.domain.model

data class Products(
    val products: List<Product>,
    val hasMore: Boolean,
)
