package woowacourse.shopping.view.cart

import androidx.annotation.LayoutRes
import woowacourse.shopping.R

enum class CartViewType(@LayoutRes val id: Int) {
    CART_PRODUCT_ITEM(R.layout.item_cart),
    PAGINATION_ITEM(R.layout.item_cart_pagination),
}
