package woowacourse.shopping.data.cartRepository

import android.content.Context
import androidx.room.Room
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

    override fun addProduct(product: Product) {
        thread {
            val cartEntity = product.toEntity()
            cartDao.insert(cartEntity)
        }
    }

    companion object {
        private const val DB_NAME = "cart"

        private var instance: CartRepositoryImpl? = null

        fun initialize(context: Context): CartRepositoryImpl = instance ?: CartRepositoryImpl(context).also { instance = it }

        fun get(): CartRepositoryImpl = instance ?: throw IllegalArgumentException("CartRepository가 초기화되지 않았습니다.")
    }
}
