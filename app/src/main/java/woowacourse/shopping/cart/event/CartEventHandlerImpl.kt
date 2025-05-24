package woowacourse.shopping.cart.event

import woowacourse.shopping.cart.CartViewModel
import woowacourse.shopping.product.ProductQuantityHandler
import woowacourse.shopping.product.catalog.ProductUiModel

class CartEventHandlerImpl(
    private val viewModel: CartViewModel,
) : CartEventHandler,
    ProductQuantityHandler {
    override fun onDeleteProduct(product: ProductUiModel) = viewModel.onDeleteProduct(product)

    override fun onNextPage() = viewModel.onNextPage()

    override fun onPrevPage() = viewModel.onPrevPage()

    override fun isNextButtonEnabled() = viewModel.isNextButtonEnabled()

    override fun isPrevButtonEnabled() = viewModel.isPrevButtonEnabled()

    override fun isPaginationEnabled(): Boolean = viewModel.isPaginationEnabled()

    override fun getPage(): Int = viewModel.getPage()

    override fun onPlusQuantity(product: ProductUiModel) = viewModel.increaseQuantity(product)

    override fun onMinusQuantity(product: ProductUiModel) = viewModel.decreaseQuantity(product)
}
