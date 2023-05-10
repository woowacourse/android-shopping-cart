package woowacourse.shopping.presentation.view.cart

import woowacourse.shopping.data.respository.cart.CartRepository

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository
) : CartContract.Presenter {
    private val carts = cartRepository.getCarts().toMutableList()

    override fun loadCartItems() {
        view.setCartItemsView(carts)
    }

    override fun deleteCartItem(position: Int) {
        if (position >= 0) {
            cartRepository.deleteCartByProductId(carts[position].product.id)
            carts.removeAt(position)
            view.updateToDeleteCartItemView(position)
        }
    }
}
