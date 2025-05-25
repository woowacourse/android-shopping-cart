package woowacourse.shopping

import android.content.Context
import woowacourse.shopping.data.CartRepositoryImpl
import woowacourse.shopping.data.ProductRepositoryImpl
import woowacourse.shopping.data.db.ShoppingDatabase
import woowacourse.shopping.data.dummyProducts
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

object RepositoryProvider {
    private const val NOT_INITIALIZED_MESSAGE = "%s가 초기화되지 않았습니다"

    private var _cartRepository: CartRepository? = null
    val cartRepository
        get() =
            requireNotNull(_cartRepository) {
                NOT_INITIALIZED_MESSAGE.format(
                    CartRepository::class.simpleName,
                )
            }

    private var _productRepository: ProductRepository? = null
    val productRepository
        get() =
            requireNotNull(_productRepository) {
                NOT_INITIALIZED_MESSAGE.format(
                    ProductRepository::class.simpleName,
                )
            }

    private var isInitialized = false

    fun initialize(context: Context) {
        if (isInitialized) return

        val database = ShoppingDatabase.getDatabase(context.applicationContext)
        val cartDao = database.cartDao()
        val recentProductDao = database.recentProductDao()

        _cartRepository = CartRepositoryImpl(cartDao)
        _productRepository = ProductRepositoryImpl(dummyProducts, cartDao, recentProductDao)

        isInitialized = true
    }

    fun initCartRepository(repository: CartRepository) {
        _cartRepository = repository
    }

    fun initProductRepository(repository: ProductRepository) {
        _productRepository = repository
    }
}
