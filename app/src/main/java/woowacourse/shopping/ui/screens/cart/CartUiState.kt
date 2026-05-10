package woowacourse.shopping.ui.screens.cart

data class CartUiState(
    val curPage: Int = 1,
    val isLast: Boolean = true,
    val items: List<CartUiModel> = emptyList(),
    val isLoading: Boolean = false,
)
