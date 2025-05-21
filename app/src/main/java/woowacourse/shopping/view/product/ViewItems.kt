package woowacourse.shopping.view.product

import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.product.ViewItems.ViewType.entries

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

    enum class ViewType {
        PRODUCTS,
        SHOW_MORE, ;

        companion object {
            fun from(viewType: Int): ViewType = entries[viewType]
        }
    }
}
