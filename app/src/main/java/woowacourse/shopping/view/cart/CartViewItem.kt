package woowacourse.shopping.view.cart

import woowacourse.shopping.model.CartPageStatus
import woowacourse.shopping.model.CartProductModel

sealed interface CartViewItem {
    val type: CartViewType
    data class CartProductItem(val product: CartProductModel) : CartViewItem {
        override val type = CartViewType.CART_PRODUCT_ITEM
    }
    data class PaginationItem(val status: CartPageStatus) : CartViewItem {
        override val type = CartViewType.PAGINATION_ITEM
    }
}
