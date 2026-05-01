package woowacourse.shopping.repository.cart

import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.CartItem
import woowacourse.shopping.domain.cart.CartItems

object MemoryCartRepository : CartRepository {
    private var cart: Cart = Cart(cartItems = CartItems(mutableListOf()))

    override suspend fun getCart(): Cart = cart

    override suspend fun addCartItem(cartItem: CartItem) {
        cart = cart.addCartItem(cartItem)
    }

    override suspend fun removeCartItem(cartItem: CartItem) {
        cart = cart.removeCartItem(cartItem)
    }
}
