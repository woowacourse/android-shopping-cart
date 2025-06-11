package woowacourse.shopping.view.product

import woowacourse.shopping.data.product.ProductImageUrls.imageUrl
import woowacourse.shopping.domain.product.Product

sealed interface ProductsItem {
    val viewType: ItemType

    data class RecentViewedProductsItem(
        val products: List<Product>,
    ) : ProductsItem {
        override val viewType: ItemType = ItemType.RECENT_VIEWED_PRODUCT
    }

    data class ProductItem(
        val product: Product,
        var quantity: Int,
    ) : ProductsItem {
        override val viewType: ItemType = ItemType.PRODUCT
        val name = product.name
        val price = product.price
        val imageUrl = product.imageUrl
    }

    data class LoadItem(
        val loadable: Boolean,
    ) : ProductsItem {
        override val viewType: ItemType = ItemType.MORE
    }

    enum class ItemType {
        RECENT_VIEWED_PRODUCT,
        PRODUCT,
        MORE,
        ;

        companion object {
            fun from(viewType: Int): ItemType = entries[viewType]
        }
    }
}
