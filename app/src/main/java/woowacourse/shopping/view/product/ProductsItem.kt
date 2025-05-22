package woowacourse.shopping.view.product

import woowacourse.shopping.data.product.ProductImageUrls.imageUrl
import woowacourse.shopping.domain.product.CartItem

sealed interface ProductsItem {
    val viewType: ItemType

    data class ProductItem(
        val cartItem: CartItem,
    ) : ProductsItem {
        override val viewType: ItemType = ItemType.PRODUCT
        val imageUrl = cartItem.imageUrl
        var quantity: Int = cartItem.quantity
    }

    data class LoadItem(
        val loadable: Boolean,
    ) : ProductsItem {
        override val viewType: ItemType = ItemType.MORE
    }

    enum class ItemType {
        PRODUCT,
        MORE,
        ;

        companion object {
            fun from(viewType: Int): ItemType = entries[viewType]
        }
    }
}
