package woowacourse.shopping.view.product.catalog

import woowacourse.shopping.domain.ShoppingProduct

sealed class ProductItem {
    data class CatalogProduct(
        val shoppingProduct: ShoppingProduct,
    ) : ProductItem()

    data object LoadMore : ProductItem()
}
