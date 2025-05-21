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
}
