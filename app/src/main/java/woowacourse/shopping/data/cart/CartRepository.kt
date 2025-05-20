package woowacourse.shopping.data.cart

import android.content.Context
import androidx.room.Room
import woowacourse.shopping.data.product.ProductEntity
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.utils.toProduct
import woowacourse.shopping.utils.toProductEntity

private const val DATABASE_NAME = "cart-db"

class CartRepository private constructor(context: Context) {

    private val database: CartDatabase = Room.databaseBuilder(
        context.applicationContext,
        CartDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val cartDao = database.cartDao()

    fun getProducts(): List<ProductEntity> = cartDao.findAll()

    fun getProduct(id: Long): Product = cartDao.findById(id).toProduct()

    fun getPagedItems(limit: Int, offset: Int): List<Product> = cartDao.findPagedItems(limit, offset).map { it.toProduct() }

    fun insert(product: Product) = cartDao.insert(product.toProductEntity())

    fun insertAll(vararg product: Product) = cartDao.insertAll(*product.map { it.toProductEntity() }.toTypedArray())

    fun delete(product: Product) = cartDao.delete(product.toProductEntity())

    fun size(): Int = cartDao.size()

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
