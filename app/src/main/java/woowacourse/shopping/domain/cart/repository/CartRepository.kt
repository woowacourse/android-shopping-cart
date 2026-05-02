package woowacourse.shopping.domain.cart.repository

import woowacourse.shopping.domain.cart.model.Cart
import woowacourse.shopping.domain.cart.model.CartItem

interface CartRepository {
    suspend fun getCart(): Cart

    suspend fun addCartItem(cartItem: CartItem)

    suspend fun removeCartItem(cartItem: CartItem)
}
