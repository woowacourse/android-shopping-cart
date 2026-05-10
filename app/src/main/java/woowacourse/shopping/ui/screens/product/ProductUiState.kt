package woowacourse.shopping.ui.screens.product

data class ProductUiState(
    val products: List<ProductUiModel> = emptyList(),
    val totalCartAmount: Int = 0,
    val hasNext: Boolean = true,
    val isLoading: Boolean = false,
)
