package woowacourse.shopping.view.products

import woowacourse.shopping.model.products.Product

sealed class ProductListViewType {
    data class ProductType(
        val product: Product,
    ) : ProductListViewType()

    data object LoadMoreType : ProductListViewType()
}
