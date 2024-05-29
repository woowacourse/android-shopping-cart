package woowacourse.shopping.data.datasource.cart

import woowacourse.shopping.data.db.cart.Cart

interface CartDataSource {
    fun getCarts(): List<Cart>?

    fun getCart(productId: Long): Cart?

    fun getCartsByPage(
        page: Int,
        pageSize: Int,
    ): List<Cart>

    fun addCart(
        productId: Long,
        quantity: Int,
    )

    fun removeCart(productId: Long)

    fun removeCarts()

    fun totalCartCount(): Int

    fun plusCartQuantity(
        productId: Long,
        quantity: Int,
    )

    fun minusCartQuantity(
        productId: Long,
        quantity: Int,
    )
}
