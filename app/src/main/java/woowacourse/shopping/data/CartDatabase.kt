package woowacourse.shopping.data

import woowacourse.shopping.product.catalog.ProductUiModel
import woowacourse.shopping.product.detail.CartUiState

object CartDatabase : CartProductDataSource {
    private val _cartProducts: MutableList<ProductUiModel> = mutableListOf()
    val cartProducts: List<ProductUiModel> get() = _cartProducts.toList()

    override fun insertCartProduct(cartProduct: ProductUiModel): CartUiState {
        val beforeInsertSize = _cartProducts.size
        _cartProducts.add(cartProduct)

        return if (beforeInsertSize < _cartProducts.size) {
            CartUiState.SUCCESS
        } else {
            CartUiState.FAIL
        }
    }

    override fun deleteCartProduct(cartProduct: ProductUiModel) {
        _cartProducts.remove(cartProduct)
    }

    override fun cartProducts(): List<ProductUiModel> = cartProducts
}
