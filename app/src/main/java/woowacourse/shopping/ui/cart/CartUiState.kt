package woowacourse.shopping.ui.cart

import woowacourse.shopping.domain.cart.CartItem

sealed interface CartUiState {
    data object Loading : CartUiState

    data object Empty : CartUiState

    data class Success(
        val cartItems: List<CartItem>,
        val currentPage: Int,
        val totalPages: Int,
        val hasPrevious: Boolean,
        val hasNext: Boolean,
    ) : CartUiState {
        val showPageNavigator: Boolean
            get() = totalPages > 1
    }

    data class Error(
        val throwable: Throwable,
    ) : CartUiState
}
