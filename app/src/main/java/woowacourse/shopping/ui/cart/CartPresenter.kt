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

    override fun loadCartItems(limit: Int, page: Int) {
        val offset = (page - 1) * limit
        view.setCartItems(
            cartRepository.findAll(limit = limit, offset = offset).map(CartUIState::from),
        )
    }

    override fun deleteCartItem(productId: Long) {
        cartRepository.deleteById(productId)
        view.setCartItems(cartRepository.findAll().map(CartUIState::from))
    }

    override fun setPageButtons(limit: Int) {
        var size = cartRepository.findAll().size
        if (size == 0) size = 1
        view.setButtonClickListener((size - 1) / limit + 1)
    }
}
