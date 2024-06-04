package woowacourse.shopping.data.repository.cart

import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.model.cart.CartItem
import woowacourse.shopping.data.model.cart.CartedProduct
import woowacourse.shopping.data.model.product.CartableProduct
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

    override fun fetchTotalCount(): Int {
        var count = 0
        thread {
            count = cartDataSource.fetchTotalCount()
        }.join()
        return count
    }

    override fun removeAll() {
        thread {
            cartDataSource.removeAll()
        }.join()
    }

    override fun patchQuantity(
        productId: Long,
        quantity: Int,
        cartItem: CartItem?,
    ) {
        thread {
            if (quantity == 0) {
                if (cartItem != null) {
                    cartDataSource.removeCartItem(cartItem)
                }
            } else {
                if (cartItem?.id != null) {
                    cartDataSource.updateQuantity(cartItem.id, quantity)
                } else {
                    cartDataSource.addCartItem(CartItem(productId = productId, quantity = quantity))
                }
            }
        }.join()
    }

    override fun addCartItem(cartItem: CartItem) {
        thread {
            cartDataSource.addCartItem(cartItem)
        }.join()
    }
}
