package woowacourse.shopping.view.product

import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.product.ProductsItem.ItemType.entries

sealed interface ProductsItem {
    val viewType: ItemType

    data class ProductItem(
        val product: Product,
    ) : ProductsItem {
        override val viewType: ItemType = ItemType.PRODUCT
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
