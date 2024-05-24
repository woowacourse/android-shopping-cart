package woowacourse.shopping.data.datasource

import woowacourse.shopping.db.cart.Cart
import woowacourse.shopping.db.cart.CartDatabase
import kotlin.concurrent.thread
import kotlin.math.min

class DefaultCart(
    cartDatabase: CartDatabase,
) : CartDataSource {
    private val cartDao = cartDatabase.cartDao()

    override fun getCarts(): List<Cart>? {
        var carts: List<Cart>? = null

        thread {
            carts = cartDao.getAll()
        }.join()

        return carts
    }

    override fun totalCartCount(): Int {
        var count = 0

        thread {
            count = cartDao.getAll().size
        }.join()

        return count
    }

    override fun getCart(productId: Long): Cart? {
        var cart: Cart? = null

        thread {
            cart = cartDao.getCart(productId)
        }.join()

        return cart
    }

    override fun addCart(
        productId: Long,
        quantity: Int,
    ): Long {
        thread {
            val cart = cartDao.getCart(productId)

            if (cart == null) {
                cartDao.insert(Cart(productId = productId, quantity = quantity))
            } else {
                cartDao.update(cart.copy(quantity = quantity))
            }
        }.join()

        return productId
    }

    override fun plusCartQuantity(
        productId: Long,
        quantity: Int,
    ): Long {
        thread {
            val cart = cartDao.getCart(productId)
            cart?.let {
                cartDao.update(cart.copy(quantity = cart.quantity + 1))
            }
        }.join()
        return productId
    }

    override fun minusCartQuantity(
        productId: Long,
        quantity: Int,
    ): Long {
        thread {
            val cart = cartDao.getCart(productId)
            cart?.let {
                cartDao.update(cart.copy(quantity = cart.quantity - 1))
            }
        }.join()
        return productId
    }

    override fun removeCart(productId: Long): Long {
        thread {
            val cart = cartDao.getCart(productId)
            cartDao.delete(cart!!)
        }.join()
        return productId
    }

    override fun removeCarts() {
        thread {
            cartDao.deleteAll()
        }.join()
    }

    override fun getCartsByPage(
        page: Int,
        pageSize: Int,
    ): List<Cart> {
        var carts: List<Cart> = emptyList()
        thread {
            carts = cartDao.getAll()
        }.join()

        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, carts.size)

        if (fromIndex > toIndex) return emptyList()

        return carts.subList(fromIndex, toIndex)
    }
}
