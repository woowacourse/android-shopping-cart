package woowacourse.shopping.domain.model

data class Products(
    val products: List<Product>,
    val hasMore: Boolean,
) {
    companion object {
        val EMPTY_PRODUCTS =
            Products(
                products = emptyList(),
                hasMore = false,
            )
    }
}
