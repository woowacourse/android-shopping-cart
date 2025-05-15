package woowacourse.shopping.data

import woowacourse.shopping.product.catalog.ProductUiModel

interface CartProductDataSource {
    fun insertCartProduct(cartProduct: ProductUiModel)

    fun deleteCartProduct(cartProduct: ProductUiModel)

    fun cartProducts(): List<ProductUiModel>
}
