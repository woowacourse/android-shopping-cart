package woowacourse.shopping.view.inventory.item

import woowacourse.shopping.domain.RecentProduct

sealed interface InventoryItem {
    val type: InventoryItemType

    data class InventoryProduct(
        val id: Int,
        val name: String,
        val price: Int,
        val quantity: Int,
        val imageUrl: String,
    ) : InventoryItem {
        override val type = InventoryItemType.PRODUCT
    }

    data object ShowMore : InventoryItem {
        override val type = InventoryItemType.SHOW_MORE
    }

    data class RecentProducts(val recentProducts: List<RecentProduct>) : InventoryItem {
        override val type = InventoryItemType.RECENT_PRODUCTS
    }
}
