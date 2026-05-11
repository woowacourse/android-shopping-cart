package woowacourse.shopping.shoppingcart

data class ShoppingCartUiState(
    val shoppingCartItems: List<CartItemUiModel>,
    val currentPage: Int,
    val canMoveToPreviousPage: Boolean,
    val canMoveToNextPage: Boolean,
)
