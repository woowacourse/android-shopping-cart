package woowacourse.shopping.presentation.ui.products

import woowacourse.shopping.domain.model.Product

sealed class ProductsItem(
    val viewType: ProductsItemViewType,
) {
    data class ProductProductsItem(
        val value: Product,
    ) : ProductsItem(ProductsItemViewType.PRODUCT)

    data object LoadMoreProductsItem : ProductsItem(ProductsItemViewType.LOAD_MORE)
}
