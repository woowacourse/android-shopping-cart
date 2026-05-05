package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.cart.CartItem
import woowacourse.shopping.domain.model.cart.CartItems

interface CartRepository {
    fun getCartItems(): CartItems

    fun getCartItem(productId: String): CartItem?

    fun addCartItem(cartItem: CartItem)

    fun deleteCartItem(productId: String)

    fun getCartItemCount(): Int

    fun getPagingCartItems(
        page: Int,
        pageSize: Int = 5,
    ): CartItems

    fun saveCartItems(cartItems: CartItems)
}
