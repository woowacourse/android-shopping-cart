package woowacourse.shopping.cart.fixture

import woowacourse.shopping.data.CartProductDataSource
import woowacourse.shopping.product.catalog.ProductUiModel
import woowacourse.shopping.product.detail.CartUiState

class FakeCartProductDataSource(
    initialProducts: List<ProductUiModel>,
) : CartProductDataSource {
    private val products = initialProducts.toMutableList()

    override fun cartProducts(): List<ProductUiModel> = products.toList()

    override fun insertCartProduct(cartProduct: ProductUiModel): CartUiState {
        products.add(cartProduct)
        return CartUiState.SUCCESS
    }

    override fun deleteCartProduct(product: ProductUiModel) {
        products.remove(product)
    }
}
