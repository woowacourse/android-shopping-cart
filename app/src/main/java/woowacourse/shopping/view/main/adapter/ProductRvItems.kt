package woowacourse.shopping.view.main.adapter

import woowacourse.shopping.view.main.state.HistoryState
import woowacourse.shopping.view.main.state.ProductState

sealed class ProductRvItems(val viewType: ViewType) {
    data class RecentProductItem(
        val items: List<HistoryState>,
    ) : ProductRvItems(ViewType.VIEW_TYPE_RECENT_PRODUCT)

    object DividerItem : ProductRvItems(ViewType.VIEW_TYPE_DIVIDER)

    data class ProductItem(
        val data: ProductState,
    ) : ProductRvItems(ViewType.VIEW_TYPE_PRODUCT)

    object LoadItem : ProductRvItems(ViewType.VIEW_TYPE_LOAD)

    enum class ViewType {
        VIEW_TYPE_RECENT_PRODUCT,
        VIEW_TYPE_DIVIDER,
        VIEW_TYPE_PRODUCT,
        VIEW_TYPE_LOAD,
    }
}
