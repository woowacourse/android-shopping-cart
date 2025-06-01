package woowacourse.shopping.data.cart

import woowacourse.shopping.data.runAsyncResult
import woowacourse.shopping.model.cart.CartItem
import kotlin.concurrent.thread

class CartRepositoryImpl(
    private val cartDao: CartDao,
) : CartRepository {
    override fun getAll(callback: (Result<List<CartItem>>) -> Unit) {
        runAsyncResult(function = {
            cartDao.getAll().map { it.toCartItem() }
        }, callback)
    }

    override fun add(
        cartItem: CartItem,
        callback: (Result<Unit>) -> Unit,
    ) {
        runAsyncResult(function = {
            val existing = cartDao.findItemById(cartItem.product.id)
            if (existing == null) {
                cartDao.insert(cartItem.toCartItemEntity())
            } else {
                cartDao.update(cartItem.product.id, cartItem.quantity)
            }
        }, callback)
    }

    override fun remove(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    ) {
        runAsyncResult(function = { cartDao.delete(productId) }, callback)
    }

    override fun update(
        productId: Long,
        quantityIncrease: Int,
        callback: (Result<Unit>) -> Unit,
    ) {
        runAsyncResult(function = { cartDao.update(productId, quantityIncrease) }, callback)
    }

    override fun fetchProducts(
        offset: Int,
        limit: Int,
        callback: (Result<List<CartItem>>) -> Unit,
    ) {
        runAsyncResult(
            function = { cartDao.fetchProducts(offset, limit).map { it.toCartItem() } },
            callback,
        )
    }

    override fun clear() {
        thread {
            cartDao.clear()
        }
    }

    override fun findQuantityById(
        productId: Long,
        callback: (Result<Int>) -> Unit,
    ) {
        runAsyncResult(function = { cartDao.findQuantityById(productId) }, callback)
    }
}
