package woowacourse.shopping.presentation.view.cart

import woowacourse.shopping.data.respository.cart.CartRepository

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository
) : CartContract.Presenter {
    private val carts = cartRepository.getCarts(0).toMutableList()

    override fun loadCartItems() {
        view.setCartItemsView(carts)
    }

    override fun deleteCartItem(position: Int) {
        if (position >= 0) {
            cartRepository.deleteCartByProductId(carts[position].product.id)
            carts.removeAt(position)
            view.updateToDeleteCartItemView(position % 3 - 1)
        }
    }

    override fun updateCartItem(currentPage: Int) {
        val startPosition = (currentPage - 1) * 3
        if (carts.getOrNull(startPosition) == null) {
            carts.addAll(cartRepository.getCarts(startPosition))
        }
        val endPosition =
            if (carts.size in (startPosition..startPosition + 3)) carts.size else startPosition + 3
        view.updateCartItemView(carts.subList(startPosition, endPosition))
    }
}
