package woowacourse.shopping.data.local.repository

import woowacourse.shopping.data.local.dao.CartItemDao
import woowacourse.shopping.data.remote.ProductDao
import woowacourse.shopping.data.local.entity.CartItemEntity
import woowacourse.shopping.domain.product.CartItem
import kotlin.concurrent.thread

class CartRepository(
    private val cartDao: CartItemDao,
    private val productDao: ProductDao,
) {
    fun getCartItemById(id: Long, callback: (CartItem) -> Unit) {
        thread {
            val cartItem = cartDao.findById(id) ?: throw IllegalArgumentException("")
            val product = productDao.findById(id) ?: throw IllegalArgumentException("")
            callback(cartItem.toDomain(product))
        }
    }

    fun getPagedItems(limit: Int, offset: Int, callback: (List<CartItem>) -> Unit) {
        thread {
            val cartItemEntities = cartDao.findPagedItems(limit, offset)
            val cartItems = cartItemEntities.map { it.toDomain(productDao.findById(it.id) ?: return@thread) }
            callback(cartItems)
        }
    }

    fun upsert(cartItem: CartItem, callback: (CartItem) -> Unit) {
        thread {
            cartDao.upsert(CartItemEntity(cartItem.id, cartItem.quantity))
            callback(cartItem)
        }
    }

    fun remove(cartItem: CartItem, callback: (CartItem) -> Unit) {
        thread {
            cartDao.delete(CartItemEntity(cartItem.id, cartItem.quantity))
            callback(cartItem)
        }
    }

    fun removeById(id: Long, callback: (Long) -> Unit) {
        thread {
            cartDao.deleteById(id)
            callback(id)
        }
    }

    fun getSize(callback: (Int) -> Unit) {
        thread {
            val size = cartDao.size()
            callback(size)
        }
    }
}
