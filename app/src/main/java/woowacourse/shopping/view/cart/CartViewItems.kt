package woowacourse.shopping.view.cart

import woowacourse.shopping.view.cart.CartViewItems.CartViewType.entries

sealed interface CartViewItems {
    val viewType: CartViewType

    data class CartItem(
        val cartItem: CartItem,
    ) : CartViewItems {
        override val viewType: CartViewType = CartViewType.CART_ITEMS
    }

    data object Navigator : CartViewItems {
        override val viewType: CartViewType = CartViewType.NAVIGATOR
    }

    enum class CartViewType {
        CART_ITEMS,
        NAVIGATOR, ;

        companion object {
            fun from(viewType: Int): CartViewType = entries[viewType]
        }
    }
}
