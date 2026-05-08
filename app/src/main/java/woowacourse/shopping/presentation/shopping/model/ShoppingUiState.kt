package woowacourse.shopping.presentation.shopping.model

data class ShoppingUiState(
    val products: List<ShoppingItemUiModel> = emptyList(),
    val canLoadMore: Boolean = true,
    val isLoading: Boolean = false,
    val offset: Int = 0,
    val totalQuantity: Int = 0,
)
