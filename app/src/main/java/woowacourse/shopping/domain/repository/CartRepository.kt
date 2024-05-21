package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.CartItem

interface CartRepository {
    fun fetchCartItem(productId: Long): CartItem?

    fun fetchCartItems(
        page: Int,
        pageSize: Int,
    ): List<CartItem>

    fun addCartItem(
        productId: Long,
        quantity: Int,
    ): Long

    fun plusCartItem(
        productId: Long,
        quantity: Int,
    ): Long

    fun minusCartItem(
        productId: Long,
        quantity: Int,
    ): Long

    fun removeAllCartItem(productId: Long): Long
}
