package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(
    private val cartDataSource: CartDataSource,
) : CartRepository {
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

    override fun removeCartItem(cartItemId: Long): Long {
        return cartDataSource.removeAllCartItem(cartItemId)
    }
}
