package woowacourse.shopping.ui.productlist

import woowacourse.shopping.domain.product.Product

sealed class ProductListViewType {
    data class ProductItemType(val product: Product, val quantity: Int) : ProductListViewType()

    data object LoadMoreType : ProductListViewType()
}
