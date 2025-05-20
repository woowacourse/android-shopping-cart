package woowacourse.shopping.view.product.catalog

import woowacourse.shopping.domain.Product

sealed class ProductItem {
    data class CatalogProduct(
        val product: Product,
    ) : ProductItem()

    data object LoadMore : ProductItem()
}
