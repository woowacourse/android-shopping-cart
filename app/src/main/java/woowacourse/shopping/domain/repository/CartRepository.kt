package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.cart.CartItems

interface CartRepository {
    fun getCartItems(page: Int, pageSize: Int = 5): CartItems

    fun saveCartItems(cartItems: CartItems)
}
