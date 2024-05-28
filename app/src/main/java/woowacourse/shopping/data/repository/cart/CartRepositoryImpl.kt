package woowacourse.shopping.data.repository.cart

import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.model.cart.CartItem
import woowacourse.shopping.data.model.cart.CartedProduct
import woowacourse.shopping.domain.repository.cart.CartRepository

class CartRepositoryImpl(
    private val cartDataSource: CartDataSource,
) : CartRepository {
    override fun fetchCartItems(page: Int): List<CartedProduct> {
        return cartDataSource.fetchCartItems(page)
    }

    override fun addCartItem(cartItem: CartItem) {
        cartDataSource.addCartItem(cartItem)
    }

    override fun fetchTotalCount(): Int {
        return cartDataSource.fetchTotalCount()
    }

    override fun updateQuantity(
        cartItemId: Long,
        quantity: Int,
    ) {
        cartDataSource.updateQuantity(cartItemId, quantity)
    }

    override fun removeCartItem(cartItem: CartItem) {
        cartDataSource.removeCartItem(cartItem)
    }

    override fun removeAll() {
        cartDataSource.removeAll()
    }
}
