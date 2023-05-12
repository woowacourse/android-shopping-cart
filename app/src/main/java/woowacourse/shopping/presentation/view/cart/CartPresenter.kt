package woowacourse.shopping.presentation.view.cart

import android.util.Log
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
            Log.d("test", "position: $position")
            Log.d("test", "calc po: ${position % 3}")
            view.updateToDeleteCartItemView(position % 3 - 1)
        }
    }

    override fun updateCartItem(currentPage: Int) {
        val startPosition = (currentPage - 1) * 3
        if (currentPage * 3 > carts.size) {
            carts.addAll(cartRepository.getCarts(currentPage))
        }
        val endIndex = if (carts.size > startPosition + 3) 3 else carts.size - 1
        view.updateCartItemView(carts.subList(startPosition, endIndex))
    }
}
