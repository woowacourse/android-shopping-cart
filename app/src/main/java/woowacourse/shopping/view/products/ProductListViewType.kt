package woowacourse.shopping.view.products

import woowacourse.shopping.model.products.Product

sealed class ProductListViewType {
    data class ProductType(
        val product: Product,
        val quantity: Int = 0,
    ) : ProductListViewType()

    data object LoadMoreType : ProductListViewType()
}
