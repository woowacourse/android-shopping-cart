package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.CartItem

interface CartRepository {
    fun fetchCartItems(page: Int): List<CartItem>

    fun fetchCartItem(cartItemId: Long): CartItem

    fun addCartItem(cartItem: CartItem): Long

    fun fetchTotalCount(): Int

    fun updateQuantity(
        cartItemId: Long,
        quantity: Int
    )

    fun removeCartItem(cartItem: CartItem)

    fun removeAll()
}
