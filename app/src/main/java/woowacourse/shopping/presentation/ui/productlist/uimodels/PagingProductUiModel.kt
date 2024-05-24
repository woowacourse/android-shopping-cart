package woowacourse.shopping.presentation.ui.productlist.uimodels

data class PagingProductUiModel(
    val currentPage: Int,
    val productUiModels: List<ProductUiModel>,
    val isLastPage: Boolean,
)
