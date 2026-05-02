package woowacourse.shopping.ui.cart

import woowacourse.shopping.model.Cart

data class CartUiState(
    val cart: Cart = Cart(emptyMap()),
    val currentPage: Int = 1,
    val totalPages: Int = 0,
    val hasPrevious: Boolean = false,
    val hasNext: Boolean = false,
    val isLoading: Boolean = false,
)