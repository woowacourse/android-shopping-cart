package woowacourse.shopping.view.productlist

import androidx.annotation.LayoutRes
import woowacourse.shopping.R

enum class ProductListViewType(@LayoutRes val id: Int) {
    RECENT_VIEWED_ITEM(R.layout.item_recent_viewed),
    PRODUCT_ITEM(R.layout.item_product),
    SHOW_MORE_ITEM(R.layout.item_show_more),
}
