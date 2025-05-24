package woowacourse.shopping.view.inventory.item

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

sealed interface InventoryItem {
    val type: InventoryItemType

    @Parcelize
    data class InventoryProduct(
        val id: Int,
        val name: String,
        val price: Int,
        val quantity: Int,
        val imageUrl: String,
    ) : InventoryItem, Parcelable {
        @IgnoredOnParcel
        override val type = InventoryItemType.PRODUCT
    }

    data object ShowMore : InventoryItem {
        override val type = InventoryItemType.SHOW_MORE
    }

    data object RecentItemsList : InventoryItem {
        override val type = InventoryItemType.RECENT_ITEMS_LIST
    }
}
