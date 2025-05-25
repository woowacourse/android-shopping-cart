package woowacourse.shopping.data

import woowacourse.shopping.product.catalog.ProductUiModel

interface CatalogProductRepository {
    fun getAllProductsSize(): Int

    fun getProductsInRange(
        startIndex: Int,
        endIndex: Int,
    ): List<ProductUiModel>

    fun changeProductQuantity(
        product: ProductUiModel,
        diff: Int,
    ): ProductUiModel

    fun initQuantity(product: ProductUiModel)

    fun getCartProductsByUids(uids: List<Int>): List<ProductUiModel>
}
