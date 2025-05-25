package woowacourse.shopping.data.cart

import android.content.Context
import androidx.room.Room
import woowacourse.shopping.data.history.HistoryEntity
import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductRepository
import woowacourse.shopping.utils.toProduct
import woowacourse.shopping.utils.toProductEntity

private const val DATABASE_NAME = "cart-db"

class CartRepository private constructor(context: Context, private val productRepository: ProductRepository) {
    private val cartDatabase: CartDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            CartDatabase::class.java,
            DATABASE_NAME,
        ).build()

    private val cartDao = cartDatabase.cartDao()
    private val cartItemDao = cartDatabase.cartItemDao()
    private val historyDao = cartDatabase.historyDao()

    fun getProducts(): List<Product> = cartDao.findAll().map { it.toProduct() }

    fun getProduct(id: Long): Product = cartDao.findById(id).toProduct()

    fun getCartItems(): List<CartItem> {
        val cartItems = cartItemDao.findAll()
        val carts = cartDao.findAll().map { it.toProduct() }
        return cartItems.map { getCartItemWithProduct(it.id) }
    }

    fun getLastRecentProduct(): Product? {
        val target = historyDao.findLast() ?: return null
        return cartDao.findById(target.id).toProduct()
    }

    fun getRecentTenProducts(): List<Product> {
        return historyDao.findRecentTen().mapNotNull { productRepository.fetchById(it.id) }
    }

    fun findByIdOrNull(id: Long): CartItemEntity = cartItemDao.findById(id)

    fun findById(id: Long): CartItem? {
        val cartItemEntity = cartItemDao.findById(id) ?: return null
        val product = cartDao.findById(id) ?: return null
        return CartItem(
            id,
            product.toProduct(),
            cartItemEntity.quantity,
        )
    }

    fun getPagedItems(
        limit: Int,
        offset: Int,
    ): List<CartItem> {
        return cartDao.findPagedItems(limit, offset).map { getCartItemWithProduct(it.id) }
    }

    fun insert(product: Product) {
        cartDao.insert(product.toProductEntity())
        cartItemDao.insert(CartItemEntity(product.id, quantity = 1))
    }

    fun insert(cartItem: CartItem) {
        cartDao.insert(cartItem.product.toProductEntity())
        cartItemDao.insert(CartItemEntity(cartItem.product.id, cartItem.quantity))
    }

    fun insertRecentProduct(id: Long) {
        val target = historyDao.findById(id)
        if (target != null) {
            historyDao.deleteById(id)
        }
        historyDao.insert(HistoryEntity(id))
    }

    fun insertAll(vararg product: Product) = cartDao.insertAll(*product.map { it.toProductEntity() }.toTypedArray())

    fun delete(id: Long) {
        val product = cartDao.findById(id)
        val cartItem = cartItemDao.findById(id)
        cartDao.delete(product)
        cartItemDao.delete(cartItem)
    }

    fun size(): Int = cartDao.size()

    fun update(newCartItem: CartItem) {
        val existingProduct = getProduct(newCartItem.id)
        val updated = CartItem(newCartItem.id, existingProduct, newCartItem.quantity)
        cartItemDao.update(CartItemEntity(updated.id, updated.quantity))
    }

    fun increaseQuantity(id: Long) {
        val cartItem = cartItemDao.findById(id)
        cartItemDao.update(cartItem.copy(quantity = cartItem.quantity + 1))
    }

    fun decreaseQuantity(id: Long) {
        val cartItem = cartItemDao.findById(id)
        if (cartItem.quantity > 1) {
            cartItemDao.update(cartItem.copy(quantity = (cartItem.quantity - 1)))
        } else {
            cartDao.deleteById(id)
            cartItemDao.deleteById(id)
        }
    }

    private fun getCartItemWithProduct(id: Long): CartItem {
        val product = cartDao.findById(id)
        val cartItem = cartItemDao.findById(id)

        return CartItem(
            id = id,
            product = product.toProduct(),
            quantity = cartItem.quantity,
        )
    }

    companion object {
        private var INSTANCE: CartRepository? = null

        fun initialize(
            context: Context,
            productRepository: ProductRepository,
        ) {
            if (INSTANCE == null) {
                INSTANCE = CartRepository(context, productRepository)
            }
        }

        fun get(): CartRepository {
            return INSTANCE
                ?: throw IllegalStateException("CartRepository가 초기화되지 않았습니다.")
        }
    }
}
