package woowacourse.shopping.domain.model

data class ProductResponse(
    val hasNext: Boolean,
    val products: List<Product>,
)
