package woowacourse.shopping.view.product.catalog.adapter

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentProduct

sealed class ProductCatalogItem(
    val type: ViewType,
) {
    data class RecentProductsItem(
        val recentProducts: List<RecentProduct>,
    ) : ProductCatalogItem(ViewType.RECENT_PRODUCT)

    data class ProductItem(
        val product: Product,
        val quantity: Int,
    ) : ProductCatalogItem(ViewType.PRODUCT)

    data object LoadMoreItem : ProductCatalogItem(ViewType.LOAD_MORE)

    enum class ViewType {
        RECENT_PRODUCT,
        PRODUCT,
        LOAD_MORE,
    }
}
