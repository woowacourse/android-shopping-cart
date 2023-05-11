package woowacourse.shopping.ui.cart

import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.ui.cart.uistate.CartUIState

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
) : CartContract.Presenter {

    override fun loadCartItems() {
        view.setCartItems(cartRepository.findAll().map(CartUIState::from))
    }

    override fun deleteCartItem(productId: Long) {
        cartRepository.deleteById(productId)
        view.setCartItems(cartRepository.findAll().map(CartUIState::from))
    }
}
