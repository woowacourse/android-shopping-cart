package woowacourse.shopping.view.shopping.adapter

import woowacourse.shopping.domain.model.Product

sealed class ShoppingType(open val viewType: Int) {
    data class ProductType(
        val product: Product,
        override val viewType: Int = PRODUCT_VIEW_TYPE,
    ) : ShoppingType(viewType)

    data class LoadMoreType(
        override val viewType: Int = LOAD_MORE_BUTTON_VIEW_TYPE,
    ) : ShoppingType(viewType)

    companion object {
        const val PRODUCT_VIEW_TYPE = 0
        const val LOAD_MORE_BUTTON_VIEW_TYPE = 1
    }
}
