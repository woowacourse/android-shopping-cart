package woowacourse.shopping.data.cart.datasource

import android.content.Context
import woowacourse.shopping.data.cart.CartProductEntity
import woowacourse.shopping.data.cart.cache.CartCache
import woowacourse.shopping.data.cart.cache.CartCacheImpl

class CartDataSourceImpl(
    context: Context,
    private val cartCache: CartCache = CartCacheImpl(context),
) : CartDataSource {

    override fun getCartProducts(): List<CartProductEntity> {

        return cartCache.getCartProducts()
    }

    override fun getCartProductById(id: Int): CartProductEntity {

        return cartCache.getCartProductById(id)
    }

    override fun getCountOfCartProducts(): Int {

        return cartCache.getCountOfCartProducts()
    }

    override fun addToCart(id: Int, count: Int) {
        cartCache.addToCart(id, count)
    }

    override fun removeCartProductById(id: Int) {
        cartCache.removeCartProductById(id)
    }
}
