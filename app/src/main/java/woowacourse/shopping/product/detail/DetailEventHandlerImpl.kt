package woowacourse.shopping.product.detail

import woowacourse.shopping.product.ProductQuantityHandler
import woowacourse.shopping.product.catalog.ProductUiModel

class DetailEventHandlerImpl(
    private val viewModel: DetailViewModel,
) : DetailEventHandler,
    ProductQuantityHandler {
    override fun onPlusQuantity(product: ProductUiModel) {
        viewModel.increaseQuantity()
    }

    override fun onMinusQuantity(product: ProductUiModel) {
        viewModel.decreaseQuantity()
    }

    override fun onAddCartItem(product: ProductUiModel) {
        viewModel.addToCart()
    }
}
