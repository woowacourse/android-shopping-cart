package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.db.cart.Cart

interface CartRepository {
    fun fetchAllCart(): List<Cart>?

    fun fetchTotalCartCount(): Int

    fun fetchCartItem(productId: Long): Cart?

    fun fetchCartItems(
        page: Int,
        pageSize: Int,
    ): List<Cart>

    fun addCartItem(
        productId: Long,
        quantity: Int,
    )

    fun plusCartItem(
        productId: Long,
        quantity: Int,
    )

    fun minusCartItem(
        productId: Long,
        quantity: Int,
    )

    fun removeAllCartItem(productId: Long)
}
