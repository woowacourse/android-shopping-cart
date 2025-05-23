package woowacourse.shopping.data.cart

import android.content.Context
import androidx.room.Room
import woowacourse.shopping.data.product.ProductEntity
import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.utils.toProduct
import woowacourse.shopping.utils.toProductEntity

private const val DATABASE_NAME = "cart-db"

class CartRepository private constructor(context: Context) {

    private val cartDatabase: CartDatabase = Room.databaseBuilder(
        context.applicationContext,
        CartDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val cartDao = cartDatabase.cartDao()
    private val cartItemDao = cartDatabase.cartItemDao()

    fun getProducts(): List<ProductEntity> = cartDao.findAll()

    fun getProduct(id: Long): Product = cartDao.findById(id).toProduct()

    fun findByIdOrNull(id: Long): CartItemEntity = cartItemDao.findById(id)

    fun getPagedItems(limit: Int, offset: Int): List<CartItem> {
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

    fun insertAll(vararg product: Product) = cartDao.insertAll(*product.map { it.toProductEntity() }.toTypedArray())

    fun delete(id: Long) {
        val product = cartDao.findById(id)
        val cartItem = cartItemDao.findById(id)
        cartDao.delete(product)
        cartItemDao.delete(cartItem)
    }

    fun size(): Int = cartDao.size()

    fun update(newCartItem: CartItem) {
        val existingItem = findByIdOrNull(newCartItem.id)
        val existingProduct = getProduct(newCartItem.id)
        if (existingItem != null) {
            val updated = CartItem(newCartItem.id, existingProduct, existingItem.quantity + newCartItem.quantity)
            update(updated)
        } else {
            insert(newCartItem)
        }
    }

    fun getCartItemWithProduct(id: Long): CartItem {
        val product = cartDao.findById(id)
        val cartItem = cartItemDao.findById(id)

        return CartItem(
            id = id,
            product = product.toProduct(),
            quantity = cartItem.quantity
        )
    }

    companion object {
        private var INSTANCE: CartRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CartRepository(context)
            }
        }

        fun get(): CartRepository {
            return INSTANCE ?:
            throw IllegalStateException("CartRepository가 초기화되지 않았습니다.")
        }
    }
}
