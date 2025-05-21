package woowacourse.shopping.product.catalog

class CatalogEventHandlerImpl(
    private val viewModel: CatalogViewModel,
    private val onNavigateToDetail: (ProductUiModel) -> Unit,
) : CatalogEventHandler {
    override fun onProductClick(product: ProductUiModel) {
        onNavigateToDetail(product)
    }

    override fun onLoadButtonClick() {
        viewModel.loadNextCatalogProducts()
    }

    override fun onOpenProductQuantitySelector(product: ProductUiModel) {
        viewModel.isQuantitySelectorExpanded(product)
    }

    override fun onPlusQuantity(product: ProductUiModel) {
        viewModel.increaseQuantity(product)
    }

    override fun onMinusQuantity(product: ProductUiModel) {
        viewModel.decreaseQuantity(product)
    }
}
