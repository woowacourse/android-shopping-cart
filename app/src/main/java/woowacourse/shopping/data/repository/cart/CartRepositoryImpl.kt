package woowacourse.shopping.data.repository.cart

import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.model.cart.CartItem
import woowacourse.shopping.data.model.cart.CartedProduct
import woowacourse.shopping.domain.repository.cart.CartRepository
import kotlin.concurrent.thread

class CartRepositoryImpl(
    private val cartDataSource: CartDataSource,
) : CartRepository {
    override fun fetchCartItems(page: Int): List<CartedProduct> {
        var products = emptyList<CartedProduct>()
        thread {
            products = cartDataSource.fetchCartItems(page)
        }.join()
        return products
    }

    override fun addCartItem(cartItem: CartItem) {
        thread {
            cartDataSource.addCartItem(cartItem)
        }.join()
    }

    override fun fetchTotalCount(): Int {
        var count = 0
        thread {
            count = cartDataSource.fetchTotalCount()
        }.join()
        return count
    }

    override fun updateQuantity(
        cartItemId: Long,
        quantity: Int,
    ) {
        thread {
            cartDataSource.updateQuantity(cartItemId, quantity)
        }.join()
    }

    override fun removeCartItem(cartItem: CartItem) {
        thread {
            cartDataSource.removeCartItem(cartItem)
        }.join()
    }

    override fun removeAll() {
        thread {
            cartDataSource.removeAll()
        }.join()
    }
}
