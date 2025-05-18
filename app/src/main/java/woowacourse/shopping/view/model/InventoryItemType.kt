package woowacourse.shopping.view.model

import androidx.annotation.LayoutRes
import woowacourse.shopping.R

enum class InventoryItemType(
    @LayoutRes val id: Int,
) {
    PRODUCT(R.layout.item_product),
    SHOW_MORE_BUTTON(R.layout.button_load_more_products),
}
