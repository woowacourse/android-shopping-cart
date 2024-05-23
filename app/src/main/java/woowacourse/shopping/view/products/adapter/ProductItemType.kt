package woowacourse.shopping.view.products.adapter

import woowacourse.shopping.domain.model.ProductWithQuantity

sealed class ProductItemType(open val viewType: Int) {
    data class ProductItem(val item: ProductWithQuantity, override val viewType: Int = PRODUCT_VIEW_TYPE) : ProductItemType(viewType)

    data class LoadMore(override val viewType: Int = LOAD_MORE_VIEW_TYPE) : ProductItemType(viewType)

    companion object {
        const val PRODUCT_VIEW_TYPE = 0
        const val LOAD_MORE_VIEW_TYPE = 1
    }
}
