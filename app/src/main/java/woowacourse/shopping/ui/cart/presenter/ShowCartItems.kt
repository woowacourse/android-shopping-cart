package woowacourse.shopping.ui.cart.presenter

import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.repository.CartItemRepository
import woowacourse.shopping.ui.cart.CartItemsView
import woowacourse.shopping.ui.cart.uistate.CartItemUIState

class ShowCartItems(
    private val view: CartItemsView,
    private val cartItemRepository: CartItemRepository,
    private val pageSize: Int
) {
    operator fun invoke(page: Int, selectedCartItems: Set<CartItem>, initScroll: Boolean) {
        val cartItems = getCartItemsOf(page)
        val cartItemUIStates =
            cartItems.map { CartItemUIState.create(it, it in selectedCartItems) }
        view.setCartItems(cartItemUIStates, initScroll)
    }

    private fun getCartItemsOf(page: Int): List<CartItem> {
        val offset = (page - 1) * pageSize
        return cartItemRepository.findAllOrderByAddedTime(pageSize, offset)
    }
}