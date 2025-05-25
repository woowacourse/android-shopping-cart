package woowacourse.shopping.data.repository

import woowacourse.shopping.product.catalog.ProductUiModel

interface CatalogProductRepository {
    fun getAllProductsSize(): Int

    fun getProductsInRange(
        startIndex: Int,
        endIndex: Int,
    ): List<ProductUiModel>

    fun getCartProductsByUids(uids: List<Int>): List<ProductUiModel>
}
