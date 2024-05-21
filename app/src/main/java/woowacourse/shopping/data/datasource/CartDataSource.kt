package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.model.CartItem

interface CartDataSource {
    fun getCartItem(productId: Long): CartItem?

    fun getCartItems(
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

    fun deleteAll()
}
