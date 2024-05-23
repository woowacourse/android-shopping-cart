package woowacourse.shopping.ui.products

import woowacourse.shopping.ui.products.adapter.ProductsViewType

sealed interface ProductsView {
    val viewType: ProductsViewType
}
