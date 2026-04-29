package woowacourse.shopping.repository.cart

import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.CartItem
import woowacourse.shopping.domain.cart.CartItems

interface CartRepository {
    suspend fun getCart(): Cart

    suspend fun getCarItems(): CartItems

    suspend fun addCartItem(cartItem: CartItem)

    suspend fun removeCartItem(cartItem: CartItem)
}
