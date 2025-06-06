package woowacourse.shopping.view.inventory.item

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentItem

sealed class InventoryItem(val viewType: ViewType) {
    data class ProductUiModel(
        val product: Product,
        val quantity: Int,
    ) : InventoryItem(ViewType.PRODUCT)

    data class RecentProducts(val recentProducts: List<RecentItem>) : InventoryItem(ViewType.RECENT_PRODUCTS)

    data object ShowMore : InventoryItem(ViewType.SHOW_MORE)

    enum class ViewType {
        PRODUCT,
        RECENT_PRODUCTS,
        SHOW_MORE,
    }
}
