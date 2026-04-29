package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.cart.CartItems

interface CartRepository {
    fun getCartItems(): CartItems

    fun saveCartItems(cartItems: CartItems)
}
