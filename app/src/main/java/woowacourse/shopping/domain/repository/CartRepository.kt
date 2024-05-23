package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.CartItem
import woowacourse.shopping.data.model.CartableProduct

interface CartRepository {
    fun fetchCartItems(page: Int): List<CartableProduct>

    fun addCartItem(cartItem: CartItem): Long

    fun fetchTotalCount(): Int

    fun updateQuantity(
        cartItemId: Long,
        quantity: Int
    )

    fun removeCartItem(cartItem: CartItem)

    fun removeAll()
}
