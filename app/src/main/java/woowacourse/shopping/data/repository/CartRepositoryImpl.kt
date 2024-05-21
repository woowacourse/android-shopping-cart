package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(
    private val cartDataSource: CartDataSource,
) : CartRepository {
    override fun fetchTotalCartCount(): Int {
        return cartDataSource.totalCartCount()
    }

    override fun fetchCartItem(productId: Long): CartItem? {
        return cartDataSource.getCartItem(productId)
    }

    override fun fetchCartItems(
        page: Int,
        pageSize: Int,
    ): List<CartItem> {
        return cartDataSource.getCartItems(page, pageSize)
    }

    override fun addCartItem(
        productId: Long,
        quantity: Int,
    ): Long {
        return cartDataSource.addCartItem(productId, quantity)
    }

    override fun plusCartItem(
        productId: Long,
        quantity: Int,
    ): Long {
        return cartDataSource.plusCartItem(productId, quantity)
    }

    override fun minusCartItem(
        productId: Long,
        quantity: Int,
    ): Long {
        return cartDataSource.minusCartItem(productId, quantity)
    }

    override fun removeAllCartItem(productId: Long): Long {
        return cartDataSource.removeAllCartItem(productId)
    }
}
