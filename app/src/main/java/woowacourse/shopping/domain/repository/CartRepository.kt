package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.CartItem
import woowacourse.shopping.data.model.CartedProduct

interface CartRepository {
    fun fetchCartItems(page: Int): List<CartedProduct>

    fun addCartItem(cartItem: CartItem)

    fun fetchTotalCount(): Int

    fun updateQuantity(
        cartItemId: Long,
        quantity: Int,
    )

    fun removeCartItem(cartItem: CartItem)
}
