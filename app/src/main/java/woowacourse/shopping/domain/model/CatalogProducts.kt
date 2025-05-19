package woowacourse.shopping.domain.model

data class CatalogProducts(
    val products: List<CartProduct>,
    val hasMore: Boolean,
) {
    companion object {
        val EMPTY_CATALOG_PRODUCTS =
            CatalogProducts(
                products = emptyList(),
                hasMore = false,
            )
    }
}
