package woowacourse.shopping.view.product.main.adapter

import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.product.main.adapter.ViewItems.ViewType.entries

sealed interface ViewItems {
    val viewType: ViewType

    data class Products(
        val product: Product,
    ) : ViewItems {
        override val viewType: ViewType = ViewType.PRODUCTS
    }

    data object ShowMore : ViewItems {
        override val viewType: ViewType = ViewType.SHOW_MORE
    }

    data class RecentlyViewedProducts(
        val products: List<Product>,
    ) : ViewItems {
        override val viewType: ViewType = ViewType.RECENTLY_VIEWED_PRODUCTS
    }

    data object Divider : ViewItems {
        override val viewType: ViewType
            get() = ViewType.DIVIDER
    }

    enum class ViewType {
        PRODUCTS,
        SHOW_MORE,
        RECENTLY_VIEWED_PRODUCTS,
        DIVIDER,
        ;

        companion object {
            fun from(viewType: Int): ViewType = entries[viewType]
        }
    }
}
