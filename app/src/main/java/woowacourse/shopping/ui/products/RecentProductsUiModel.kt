package woowacourse.shopping.ui.products

import woowacourse.shopping.ui.products.adapter.ProductsViewType
import woowacourse.shopping.ui.products.recent.RecentProductUiModel

data class RecentProductsUiModel(val recentProductUiModels: List<RecentProductUiModel>) : ProductsView {
    override val viewType: ProductsViewType = ProductsViewType.RECENT_PRODUCTS
}
