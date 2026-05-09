package woowacourse.shopping.ui.shopping

data class ShoppingUiState(
    val isLoading: Boolean = false,
    val visibleCount: Int = 0,
    val visibleProducts: List<ProductUiModel> = emptyList(),
    val hasNext: Boolean = false,
    val sizeInRepo: Int = 0,
    val cartCount: Int = 0
)
