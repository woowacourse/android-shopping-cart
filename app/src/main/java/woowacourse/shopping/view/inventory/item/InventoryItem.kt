package woowacourse.shopping.view.inventory.item

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct

sealed class InventoryItem(val type: ViewType) {
    data class ProductItem(
        val product: Product,
        val quantity: Int,
    ) : InventoryItem(ViewType.PRODUCT)

    data class RecentProductsItem(val recentProducts: List<RecentProduct>) : InventoryItem(ViewType.RECENT_PRODUCTS)

    data object ShowMore : InventoryItem(ViewType.SHOW_MORE)

    enum class ViewType {
        PRODUCT,
        SHOW_MORE,
        RECENT_PRODUCTS,
    }
}
