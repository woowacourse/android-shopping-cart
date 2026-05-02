package woowacourse.shopping.data.cart

import woowacourse.shopping.domain.cart.model.Cart
import woowacourse.shopping.domain.cart.model.CartItem
import woowacourse.shopping.domain.cart.model.CartItems
import woowacourse.shopping.domain.cart.repository.CartRepository

object CartRepositoryMockImpl : CartRepository {
    private var cart: Cart = Cart(cartItems = CartItems(mutableListOf()))

    override suspend fun getCart(): Cart = cart

    override suspend fun addCartItem(cartItem: CartItem) {
        cart = cart.addCartItem(cartItem)
    }

    override suspend fun removeCartItem(cartItem: CartItem) {
        cart = cart.removeCartItem(cartItem)
    }
}
