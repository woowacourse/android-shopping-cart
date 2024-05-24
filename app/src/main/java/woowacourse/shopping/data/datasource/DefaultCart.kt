package woowacourse.shopping.data.datasource

import woowacourse.shopping.db.Cart
import woowacourse.shopping.db.CartDatabase
import kotlin.concurrent.thread
import kotlin.math.min

class DefaultCart(
    cartDatabase: CartDatabase,
) : CartDataSource {
    private val cartDao = cartDatabase.cartDao()

    override fun getAllCartItems(): List<Cart>? {
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

    override fun getCartItem(productId: Long): Cart? {
        var cart: Cart? = null

        thread {
            cart = cartDao.getCart(productId)
        }.join()

        return cart
    }

    override fun addCartItem(
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

    override fun plusCartItem(
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

    override fun minusCartItem(
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

    override fun removeAllCartItem(productId: Long): Long {
        thread {
            val cart = cartDao.getCart(productId)
            cartDao.delete(cart!!)
        }.join()
        return productId
    }

    override fun deleteAll() {
        thread {
            cartDao.deleteAll()
        }.join()
    }

    override fun getCartItems(
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
