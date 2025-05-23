package woowacourse.shopping.product.catalog

import woowacourse.shopping.product.ProductQuantityHandler

class CatalogEventHandlerImpl(
    private val viewModel: CatalogViewModel,
    private val onNavigateToDetail: (ProductUiModel) -> Unit,
) : CatalogEventHandler,
    ProductQuantityHandler {
    override fun onProductClick(product: ProductUiModel) {
        onNavigateToDetail(product)
    }

    override fun onLoadButtonClick() {
        viewModel.loadNextCatalogProducts()
    }

    override fun onOpenProductQuantitySelector(product: ProductUiModel) {
        viewModel.onQuantitySelectorToggled(product)
    }

    override fun isProductInCart(product: ProductUiModel): Boolean = viewModel.isProductInCart(product)

    override fun onPlusQuantity(product: ProductUiModel) {
        viewModel.increaseQuantity(product)
    }

    override fun onMinusQuantity(product: ProductUiModel) {
        viewModel.decreaseQuantity(product)
    }
}
