package woowacourse.shopping.data

import woowacourse.shopping.product.catalog.ProductUiModel

interface ProductsDataSource {
    fun getProducts(): List<ProductUiModel>

    fun getSubListedProducts(
        startIndex: Int,
        lastIndex: Int,
    ): List<ProductUiModel>

    fun getProductsSize(): Int
}
