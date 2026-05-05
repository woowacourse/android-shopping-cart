package woowacourse.shopping.domain.cart.repository

import woowacourse.shopping.domain.cart.model.Cart
import woowacourse.shopping.domain.cart.model.CartItem

interface CartRepository {
    fun getCart(): Cart

    fun getTotalCartCount(): Int

    fun addCartItem(cartItem: CartItem)

    fun removeCartItem(cartItem: CartItem)
}
