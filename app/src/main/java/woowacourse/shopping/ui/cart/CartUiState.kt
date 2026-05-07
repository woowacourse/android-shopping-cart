package woowacourse.shopping.ui.cart

data class CartUiState(
    val items: List<CartItemUiModel> = emptyList(),
    val currentPage: Int = 1,
    val totalPages: Int = 0,
    val hasPrevious: Boolean = false,
    val hasNext: Boolean = false,
    val isLoading: Boolean = false,
)
