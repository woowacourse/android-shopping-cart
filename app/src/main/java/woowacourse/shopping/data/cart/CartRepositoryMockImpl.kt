package woowacourse.shopping.data.cart

import woowacourse.shopping.domain.cart.model.Cart
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.model.CartItems
import woowacourse.shopping.domain.cart.repository.CartRepository

class CartRepositoryMockImpl : CartRepository {
    private var cart: Cart = Cart(cartItems = CartItems(mutableListOf()))

    override fun getCart(): Cart = cart

    override fun getTotalCartCount(): Int = cart.totalCount

    override fun addCartItem(cartItem: CartItem) {
        cart = cart.addCartItem(cartItem)
    }

    override fun removeCartItem(cartItem: CartItem) {
        cart = cart.removeCartItem(cartItem)
    }
}
