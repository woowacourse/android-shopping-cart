package woowacourse.shopping.ui.products

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.ui.products.ProductsItemViewType.LOAD_MORE
import woowacourse.shopping.ui.products.ProductsItemViewType.PRODUCT

sealed class ProductsItem(
    val viewType: ProductsItemViewType,
) {
    data class ProductProductsItem(
        val value: Product,
    ) : ProductsItem(PRODUCT)

    data object LoadMoreProductsItem : ProductsItem(LOAD_MORE)
}
