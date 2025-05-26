package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.ShoppingCartDatabase
import woowacourse.shopping.data.cart.CartProductRepositoryImpl
import woowacourse.shopping.data.cart.local.CartProductLocalDataSource
import woowacourse.shopping.data.network.ShoppingServer
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.product.remote.ProductRemoteDataSource
import woowacourse.shopping.data.product.remote.ProductServiceImpl
import woowacourse.shopping.data.recent.RecentProductRepositoryImpl
import woowacourse.shopping.data.recent.local.RecentProductLocalDataSource
import kotlin.concurrent.thread

class ShoppingApplication : Application() {
    private val database by lazy { ShoppingCartDatabase.getDataBase(this) }

    val productRepository
        by lazy { ProductRepositoryImpl(ProductRemoteDataSource(ProductServiceImpl())) }
    val cartProductRepository
        by lazy { CartProductRepositoryImpl(CartProductLocalDataSource(database.cartProductDao)) }
    val recentProductRepository
        by lazy {
            RecentProductRepositoryImpl(
                RecentProductLocalDataSource(database.recentProductDao),
                productRepository,
            )
        }

    override fun onCreate() {
        super.onCreate()
        thread {
            ShoppingServer().startMockWebServer()
        }
    }
}
