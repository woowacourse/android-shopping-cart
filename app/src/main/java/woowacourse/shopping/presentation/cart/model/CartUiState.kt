package woowacourse.shopping.presentation.cart.model

data class CartUiState(
    val page: Int = 0,
    val currentCartItems: List<CartItemUiModel> = emptyList(),
    val isCanMoveNext: Boolean = false,
    val isLoading: Boolean = false,
    val totalCartSize: Int = 0,
    val isShowPageSection: Boolean = false,
)
