package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.CartItem

interface CartRepository {
    fun fetchCartItems(page: Int): List<CartItem>

    fun addCartItem(cartItem: CartItem): Long
}
