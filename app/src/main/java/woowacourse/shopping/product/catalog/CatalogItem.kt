package woowacourse.shopping.product.catalog

sealed class CatalogItem {
    data class ProductItem(
        val productItem: ProductUiModel,
    ) : CatalogItem()

    data object LoadMoreButtonItem : CatalogItem()
}
