package woowacourse.shopping.view.product.catalog.allproducts

import woowacourse.shopping.domain.Product

sealed class ProductItem {
    data class CatalogProduct(
        val product: Product,
        val quantity: Int,
    ) : ProductItem()

    data object LoadMore : ProductItem()
}
