package woowacourse.shopping.data

import woowacourse.shopping.product.catalog.ProductUiModel

interface CartProductDataSource {
    fun insertCartProduct(cartProduct: ProductUiModel)

    fun deleteCartProduct(cartProduct: ProductUiModel)

    fun getCartProductsInRange(
        startIndex: Int,
        endIndex: Int,
    ): List<ProductUiModel>

    fun getAllProductsSize(): Int
}
