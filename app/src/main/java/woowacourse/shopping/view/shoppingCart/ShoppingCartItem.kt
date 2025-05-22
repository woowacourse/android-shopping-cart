package woowacourse.shopping.view.shoppingCart

import woowacourse.shopping.domain.product.CartItem

sealed interface ShoppingCartItem {
    val viewType: ItemType

    data class ProductItem(
        val cartItem: CartItem,
    ) : ShoppingCartItem {
        override val viewType: ItemType = ItemType.PRODUCT
    }

    data class PaginationItem(
        val page: Int,
        val nextEnabled: Boolean,
        val previousEnabled: Boolean,
    ) : ShoppingCartItem {
        override val viewType: ItemType = ItemType.PAGINATION

        val enabled: Boolean = previousEnabled || nextEnabled
    }

    enum class ItemType {
        PRODUCT,
        PAGINATION,
        ;

        companion object {
            fun from(viewType: Int): ItemType = entries[viewType]
        }
    }
}
