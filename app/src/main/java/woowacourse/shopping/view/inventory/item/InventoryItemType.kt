package woowacourse.shopping.view.inventory.item

import androidx.annotation.LayoutRes
import woowacourse.shopping.R

enum class InventoryItemType(
    @LayoutRes val id: Int,
) {
    PRODUCT(R.layout.item_inventory_product),
    SHOW_MORE(R.layout.item_inventory_show_more),
}
