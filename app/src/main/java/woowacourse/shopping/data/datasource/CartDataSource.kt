package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.model.CartItem

interface CartDataSource {
    fun getCartItems(
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

    fun deleteAll()
}
