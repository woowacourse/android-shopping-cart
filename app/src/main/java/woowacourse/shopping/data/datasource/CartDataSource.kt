package woowacourse.shopping.data.datasource

import woowacourse.shopping.db.Cart

interface CartDataSource {
    fun getAllCartItems(): List<Cart>?

    fun totalCartCount(): Int

    fun getCartItem(productId: Long): Cart?

    fun getCartItems(
        page: Int,
        pageSize: Int,
    ): List<Cart>

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
