package woowacourse.shopping.data

import woowacourse.shopping.product.catalog.ProductUiModel
import woowacourse.shopping.product.detail.CartUiState

interface CartProductDataSource {
    fun insertCartProduct(cartProduct: ProductUiModel): CartUiState

    fun deleteCartProduct(cartProduct: ProductUiModel)

    fun cartProducts(): List<ProductUiModel>

    fun getCartProductsSize(): Int
}
