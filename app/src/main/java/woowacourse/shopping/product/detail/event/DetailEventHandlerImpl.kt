package woowacourse.shopping.product.detail.event

import woowacourse.shopping.product.ProductQuantityHandler
import woowacourse.shopping.product.catalog.ProductUiModel
import woowacourse.shopping.product.detail.DetailViewModel

class DetailEventHandlerImpl(
    private val viewModel: DetailViewModel,
    private val onNavigateToDetail: (ProductUiModel) -> Unit,
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

    override fun onProductClick(product: ProductUiModel) {
        onNavigateToDetail(product)
    }
}
