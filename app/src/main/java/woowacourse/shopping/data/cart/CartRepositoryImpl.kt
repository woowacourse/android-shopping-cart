package woowacourse.shopping.data.cart

import kotlin.concurrent.thread

class CartRepositoryImpl private constructor(private val cartDao: CartDao) : CartRepository {
    override fun insert(cart: Cart): Long {
        var id: Long = -1L
        thread {
            id = cartDao.insert(cart)
        }.join()
        return id
    }

    override fun find(id: Long): Cart {
        lateinit var cart: Cart
        thread {
            cart = cartDao.find(id)
        }.join()
        return cart
    }

    override fun findAll(): List<Cart> {
        var carts = emptyList<Cart>()
        thread {
            carts = cartDao.findAll()
        }.join()
        return carts
    }

    override fun delete(id: Long) {
        thread {
            cartDao.delete(id)
        }.join()
    }

    override fun deleteByProductId(productId: Long) {
        thread {
            cartDao.deleteByProductId(productId)
        }.join()
    }

    override fun deleteAll() {
        thread {
            cartDao.deleteAll()
        }.join()
    }

    override fun itemSize(): Int {
        var itemSize = 0
        thread {
            itemSize = cartDao.itemSize()
        }.join()
        return itemSize
    }

    override fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Cart> {
        var carts = emptyList<Cart>()
        thread {
            carts = cartDao.getProducts(page, pageSize)
        }.join()
        return carts
    }

    override fun plusQuantityByProductId(productId: Long) {
        thread {
            cartDao.plusQuantityByProductId(productId)
        }.join()
    }

    override fun minusQuantityByProductId(productId: Long) {
        thread {
            cartDao.minusQuantityByProductId(productId)
        }.join()
    }

    companion object {
        private var instance: CartRepository? = null

        fun get(cartDao: CartDao): CartRepository {
            return instance ?: synchronized(this) {
                instance ?: CartRepositoryImpl(cartDao).also { instance = it }
            }
        }
    }
}
