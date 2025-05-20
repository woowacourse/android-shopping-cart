package woowacourse.shopping.cart

import woowacourse.shopping.product.catalog.ProductUiModel

class CartEventHandlerImpl(
    private val viewModel: CartViewModel,
) : CartEventHandler {
    override fun onDeleteProduct(product: ProductUiModel) = viewModel.onDeleteProduct(product)

    override fun onNextPage() = viewModel.onNextPage()

    override fun onPrevPage() = viewModel.onPrevPage()

    override fun isNextButtonEnabled() = viewModel.isNextButtonEnabled()

    override fun isPrevButtonEnabled() = viewModel.isPrevButtonEnabled()

    override fun getPage(): Int = viewModel.page.value ?: 0
}
