package woowacourse.shopping.presentation.cart

import woowacourse.shopping.domain.model.cart.Cart

data class CartUiState(
    val cart: Cart = Cart(),
    val currentPageIndex: Int = 0,
) {
    val currentPage: Int
        get() = currentPageIndex + 1
}
