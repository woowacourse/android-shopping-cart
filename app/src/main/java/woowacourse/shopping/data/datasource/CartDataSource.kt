package woowacourse.shopping.data.datasource

import woowacourse.shopping.db.Cart

interface CartDataSource {
    fun getCarts(): List<Cart>?

    fun totalCartCount(): Int

    fun getCart(productId: Long): Cart?

    fun getCartsByPage(
        page: Int,
        pageSize: Int,
    ): List<Cart>

    fun addCart(
        productId: Long,
        quantity: Int,
    ): Long

    fun plusCartQuantity(
        productId: Long,
        quantity: Int,
    ): Long

    fun minusCartQuantity(
        productId: Long,
        quantity: Int,
    ): Long

    fun removeCart(productId: Long): Long

    fun removeCarts()
}
