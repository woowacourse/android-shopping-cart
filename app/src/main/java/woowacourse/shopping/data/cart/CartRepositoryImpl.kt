package woowacourse.shopping.data.cart

import android.os.Handler
import android.os.Looper
import woowacourse.shopping.model.cart.CartItem

class CartRepositoryImpl(
    private val cartDao: CartDao,
) : CartRepository {
    override fun getAll(callback: (List<CartItem>) -> Unit) {
        Thread {
            val values = cartDao.getAll().map { it.toCartItem() }
            callback(values)
        }.start()
    }

    override fun add(
        cartItem: CartItem,
        callback: () -> Unit,
    ) {
        Thread {
            val existing = cartDao.findItemById(cartItem.product.id)
            if (existing == null) {
                cartDao.insert(cartItem.toEntity())
            } else {
                cartDao.update(cartItem.product.id, cartItem.quantity)
            }
            Handler(Looper.getMainLooper()).post {
                callback()
            }
        }.start()
    }

    override fun remove(
        productId: Long,
        callback: () -> Unit,
    ) {
        Thread {
            cartDao.delete(productId)
            callback()
        }.start()
    }

    override fun update(
        productId: Long,
        quantityIncrease: Int,
    ) {
        Thread {
            cartDao.update(productId, quantityIncrease)
        }.start()
    }

    override fun fetchProducts(
        offset: Int,
        limit: Int,
        callback: (List<CartItem>) -> Unit,
    ) {
        Thread {
            val values = cartDao.fetchProducts(offset, limit).map { it.toCartItem() }
            callback(values)
        }.start()
    }

    override fun clear() {
        Thread {
            cartDao.clear()
        }.start()
    }

    override fun findQuantityById(
        productId: Long,
        callback: (Int) -> Unit,
    ) {
        Thread {
            val value = cartDao.findQuantityById(productId)
            callback(value)
        }.start()
    }
}
