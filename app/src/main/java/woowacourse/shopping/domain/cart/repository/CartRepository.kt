package woowacourse.shopping.domain.cart.repository

import woowacourse.shopping.domain.cart.model.Cart
import woowacourse.shopping.domain.cart.model.CartItem

interface CartRepository {
    suspend fun getCart(): Cart

    suspend fun getTotalCartCount(): Int

    suspend fun getTotalCartItemCount(): Int

    suspend fun getQuantity(cartItem: CartItem): Int

    suspend fun addCartItem(
        cartItem: CartItem,
    )

    suspend fun minusCartItem(
        cartItem: CartItem,
    )

    suspend fun removeCartItem(cartItem: CartItem)

    suspend fun isCartItemExist(cartItem: CartItem): Boolean
}
