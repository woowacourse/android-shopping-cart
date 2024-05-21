package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.CartItem

interface CartRepository {
    fun fetchCartItems(
        page: Int,
        pageSize: Int,
    ): List<CartItem>

    fun addCartItem(
        productId: Long,
        quantity: Int,
    ): Long

    fun removeCartItem(
        productId: Long,
        quantity: Int,
    ): Long

    fun removeAllCartItem(cartItemId: Long): Long
}
