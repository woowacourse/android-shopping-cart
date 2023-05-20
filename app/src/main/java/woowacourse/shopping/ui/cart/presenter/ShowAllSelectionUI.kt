package woowacourse.shopping.ui.cart.presenter

import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.repository.CartItemRepository
import woowacourse.shopping.ui.cart.AllSelectableItemView

class ShowAllSelectionUI(
    private val view: AllSelectableItemView,
    private val cartItemRepository: CartItemRepository,
    private val pageSize: Int
) {
    operator fun invoke(currentPage: Int, selectedCartItems: Set<CartItem>) {
        val cartItems = getCartItemsOf(currentPage)
        view.setStateOfAllSelection(
            cartItems.all { it in selectedCartItems } && cartItems.isNotEmpty()
        )
    }

    private fun getCartItemsOf(page: Int): List<CartItem> {
        val offset = (page - 1) * pageSize
        return cartItemRepository.findAllOrderByAddedTime(pageSize, offset)
    }
}