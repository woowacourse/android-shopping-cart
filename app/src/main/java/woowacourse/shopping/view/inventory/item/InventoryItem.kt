package woowacourse.shopping.view.inventory.item

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

sealed interface InventoryItem {
    val type: InventoryItemType

    @Parcelize
    data class ProductUiModel(
        val name: String,
        val price: Int,
        val imageUrl: String,
    ) : InventoryItem, Parcelable {
        @IgnoredOnParcel
        override val type = InventoryItemType.PRODUCT
    }

    data object ShowMore : InventoryItem {
        override val type = InventoryItemType.SHOW_MORE
    }
}
