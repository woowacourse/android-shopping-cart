package woowacourse.shopping.presentation.view.catalog.adapter

import woowacourse.shopping.presentation.model.ProductUiModel

sealed class CatalogItem(
    val viewType: CatalogType,
) {
    data class ProductItem(
        val product: ProductUiModel,
    ) : CatalogItem(CatalogType.PRODUCT)

    data object LoadMoreItem : CatalogItem(CatalogType.LOAD_MORE)

    enum class CatalogType {
        PRODUCT,
        LOAD_MORE,
    }
}
