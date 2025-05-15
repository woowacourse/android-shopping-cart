package woowacourse.shopping.data

import woowacourse.shopping.product.catalog.ProductUiModel

interface ProductsDataSource {
    fun getProducts(): List<ProductUiModel>
}
