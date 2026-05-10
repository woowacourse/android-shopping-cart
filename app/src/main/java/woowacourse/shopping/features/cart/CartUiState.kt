package woowacourse.shopping.features.cart

data class CartUiState(
    val pageCartItems: List<CartItemUiModel> = emptyList(),
    val totalPageCount: Int = 0,
    val currentPage: Int = 0,
    val isFirstPage: Boolean = true,
    val isLastPage: Boolean = true,
)
