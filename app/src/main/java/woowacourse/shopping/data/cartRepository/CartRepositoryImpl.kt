package woowacourse.shopping.data.cartRepository

import android.content.Context
import android.util.Log
import androidx.room.Room
import woowacourse.shopping.data.cartRepository.CartMapper.toDomain
import woowacourse.shopping.data.cartRepository.CartMapper.toEntity
import woowacourse.shopping.domain.Product
import kotlin.concurrent.thread

class CartRepositoryImpl private constructor(
    context: Context,
) : CartRepository {
    private val database: CartDatabase =
        Room
            .databaseBuilder(
                context,
                CartDatabase::class.java,
                DB_NAME,
            ).build()

    private val cartDao = database.cartDao()

    override fun getAllProducts(onResult: (List<Product>) -> Unit) {
        thread {
            val products = cartDao.getAllProducts().map { it.toDomain() }
            onResult(products)
        }
    }

    override fun getProducts(
        limit: Int,
        onResult: (List<Product>) -> Unit,
    ) {
        thread {
            val products = cartDao.getLimitProducts(limit).map { product -> product.toDomain() }
            onResult(products)
        }
    }

    override fun addProduct(product: Product) {
        thread {
            val cartEntity = product.toEntity()
            cartDao.insert(cartEntity)
        }
    }

    override fun deleteProduct(productId: Long) {
        thread {
            Log.d("test", "$productId")
            cartDao.deleteById(productId)
        }
    }

    companion object {
        private const val DB_NAME = "cart"

        private var instance: CartRepositoryImpl? = null

        fun initialize(context: Context): CartRepositoryImpl = instance ?: CartRepositoryImpl(context).also { instance = it }

        fun get(): CartRepositoryImpl = instance ?: throw IllegalArgumentException("CartRepository가 초기화되지 않았습니다.")
    }
}
