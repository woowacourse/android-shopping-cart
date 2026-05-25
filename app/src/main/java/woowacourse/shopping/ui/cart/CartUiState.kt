package woowacourse.shopping.ui.cart

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProducts

data class CartUiState(
    val cart: Cart = Cart(CartProducts()),
    val currentPage: Int = 0,
) {
    val hasPreviousPage: Boolean get() = currentPage > 0
    val hasNextPage: Boolean get() = currentPage < (cart.getUniqueItemCount() - 1) / ONE_PAGE_ITEM_COUNT
    val isPageable: Boolean = cart.getUniqueItemCount() > ONE_PAGE_ITEM_COUNT
    val isCartEmpty: Boolean get() = cart.getUniqueItemCount() == 0

    companion object {
        const val ONE_PAGE_ITEM_COUNT = 5
    }
}
