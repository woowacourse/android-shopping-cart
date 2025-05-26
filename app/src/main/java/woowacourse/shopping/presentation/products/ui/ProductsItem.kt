package woowacourse.shopping.presentation.products.ui

import woowacourse.shopping.domain.model.Product

sealed class ProductsItem {
    data class ProductProductsItem(
        val value: Product,
    ) : ProductsItem()

    data object LoadMoreProductsItem : ProductsItem()

    data class LastWatchProductsItem(
        val value: List<Product>,
    ) : ProductsItem()

    data object LastWatchTitleItem : ProductsItem()
}
