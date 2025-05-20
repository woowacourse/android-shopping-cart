package woowacourse.shopping.data

import woowacourse.shopping.product.catalog.ProductUiModel
import woowacourse.shopping.product.detail.CartUiState

object CartDatabase : CartProductDataSource {
    private val cartProducts: MutableList<ProductUiModel> = mutableListOf()

    override fun insertCartProduct(cartProduct: ProductUiModel): CartUiState {
        val beforeInsertSize = cartProducts.size
        cartProducts.add(cartProduct)

        return if (beforeInsertSize < cartProducts.size) {
            CartUiState.SUCCESS
        } else {
            CartUiState.FAIL
        }
    }

    override fun deleteCartProduct(cartProduct: ProductUiModel) {
        cartProducts.remove(cartProduct)
    }

    override fun cartProducts(): List<ProductUiModel> = cartProducts.toList()
}
