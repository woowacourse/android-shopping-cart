package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.cart.CartItems
import woowacourse.shopping.domain.repository.CartRepository

object CartRepositoryImpl : CartRepository {
    private var currentCart = CartItems()

    override fun getCartItems(): CartItems {
        return currentCart
    }

    override fun saveCartItems(cartItems: CartItems) {
        currentCart = cartItems
    }
}
