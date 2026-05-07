package woowacourse.shopping.ui.screens.cart

import woowacourse.shopping.domain.CartItem

data class CartUiState(
    val curPage: Int = 1,
    val isLast: Boolean = true,
    val cartItems: List<CartItem> = emptyList(),
    val isLoading: Boolean = false,
)
