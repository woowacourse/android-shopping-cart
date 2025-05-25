package woowacourse.shopping.view.inventory.item

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct

sealed interface InventoryItem {
    val type: InventoryItemType

    data class ProductItem(
        val product: Product,
        val quantity: Int,
    ) : InventoryItem {
        override val type = InventoryItemType.PRODUCT
    }

    data class RecentProductsItem(val recentProducts: List<RecentProduct>) : InventoryItem {
        override val type = InventoryItemType.RECENT_PRODUCTS
    }

    data object ShowMore : InventoryItem {
        override val type = InventoryItemType.SHOW_MORE
    }
}
