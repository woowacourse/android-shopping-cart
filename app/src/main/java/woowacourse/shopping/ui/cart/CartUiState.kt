package woowacourse.shopping.ui.cart

import woowacourse.shopping.ui.model.CartItemUiModel

data class CartUiState(
    val items: List<CartItemUiModel> = emptyList(),
    val page: Int = 0,
    val isCanMoveNext: Boolean = false,
    val totalCartSize: Int = 0,
    val totalPrice: Int = 0,
)
