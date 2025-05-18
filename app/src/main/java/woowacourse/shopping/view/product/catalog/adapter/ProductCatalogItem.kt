package woowacourse.shopping.view.product.catalog.adapter

import woowacourse.shopping.domain.Product

sealed class ProductCatalogItem(
    val type: ViewType,
) {
    data class ProductItem(
        val product: Product,
    ) : ProductCatalogItem(ViewType.PRODUCT)

    data object LoadMoreItem : ProductCatalogItem(ViewType.LOAD_MORE)

    enum class ViewType {
        PRODUCT,
        LOAD_MORE,
    }
}
