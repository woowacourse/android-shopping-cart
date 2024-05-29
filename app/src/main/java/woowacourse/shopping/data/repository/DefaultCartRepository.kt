package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.cart.CartDataSource
import woowacourse.shopping.data.db.cart.Cart
import woowacourse.shopping.domain.repository.CartRepository

class DefaultCartRepository(
    private val cartDataSource: CartDataSource,
) : CartRepository {
    override fun fetchAllCart(): List<Cart>? {
        return cartDataSource.getCarts()
    }

    override fun fetchTotalCartCount(): Int {
        return cartDataSource.totalCartCount()
    }

    override fun fetchCartItem(productId: Long): Cart? {
        return cartDataSource.getCart(productId)
    }

    override fun fetchCartItems(
        page: Int,
        pageSize: Int,
    ): List<Cart> {
        return cartDataSource.getCartsByPage(page, pageSize)
    }

    override fun addCartItem(
        productId: Long,
        quantity: Int,
    ) {
        cartDataSource.addCart(productId, quantity)
    }

    override fun plusCartItem(
        productId: Long,
        quantity: Int,
    ) {
        cartDataSource.plusCartQuantity(productId, quantity)
    }

    override fun minusCartItem(
        productId: Long,
        quantity: Int,
    ) {
        cartDataSource.minusCartQuantity(productId, quantity)
    }

    override fun removeAllCartItem(productId: Long) {
        cartDataSource.removeCart(productId)
    }
}
