package woowacourse.shopping.productlist

data class ProductListUiState(
    val productUiModels: List<ProductUiModel>,
    val viewedProductUiModels: List<ViewedProductUiModel>,
    val cartItemCount: Int,
    val isLoadMoreEnabled: Boolean,
)
