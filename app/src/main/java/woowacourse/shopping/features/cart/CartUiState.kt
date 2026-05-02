package woowacourse.shopping.features.cart

import woowacourse.shopping.domain.cart.model.CartItem

data class CartUiState(
    val cartItems: List<CartItem> = emptyList(),
    val currentPage: Int = 0,
    val totalPages: Int = 0,
    val hasPrevious: Boolean = false,
    val hasNext: Boolean = false,
)
