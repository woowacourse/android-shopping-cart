package woowacourse.shopping.data

import woowacourse.shopping.product.catalog.ProductUiModel

interface ProductsDataSource {
    fun getAllProductsSize(): Int

    fun getProductsInRange(
        startIndex: Int,
        endIndex: Int,
    ): List<ProductUiModel>

    fun changeProductQuantity(
        product: ProductUiModel,
        diff: Int,
    ): ProductUiModel
}
