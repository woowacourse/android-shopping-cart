package woowacourse.shopping.view.product

import woowacourse.shopping.domain.product.Product

sealed interface ProductsItem {
    val viewType: ItemType

    data class ProductItem(
        val product: Product,
        var quantity: Int = 0,
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
