package woowacourse.shopping.features.cart

import woowacourse.shopping.domain.cart.model.CartItem

data class CartUiState(
    val pageCartItems: List<CartItem> = emptyList(),
    val totalPageCount: Int = 0,
    val currentPage: Int = 0,
    val isFirstPage: Boolean = true,
    val isLastPage: Boolean = true,
)
