package woowacourse.shopping.ui.cart

import woowacourse.shopping.model.CartItem

data class CartUiState(
    val isLoading: Boolean = false,
    val pagedItems: List<CartItem> = emptyList(),
    val currentPage: Int = 1,
    val totalItemCount: Int = 0,
    val pageSize: Int = 5,
) {
    val totalPages: Int get() = (totalItemCount - 1) / pageSize + 1
    val showPagination: Boolean get() = totalItemCount > pageSize
}
