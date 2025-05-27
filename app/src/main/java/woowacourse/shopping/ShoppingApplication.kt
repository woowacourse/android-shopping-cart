package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.ShoppingCartDatabase
import woowacourse.shopping.data.datasource.local.CartProductLocalDataSource
import woowacourse.shopping.data.datasource.local.RecentProductLocalDataSource
import woowacourse.shopping.data.datasource.remote.ProductRemoteDataSource
import woowacourse.shopping.data.network.ShoppingServer
import woowacourse.shopping.data.repository.CartProductRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.repository.RecentProductRepositoryImpl
import woowacourse.shopping.data.service.ProductServiceImpl
import kotlin.concurrent.thread

class ShoppingApplication : Application() {
    private val database by lazy { ShoppingCartDatabase.getDataBase(this) }

    val productRepository
        by lazy { ProductRepositoryImpl(ProductRemoteDataSource(ProductServiceImpl())) }
    val cartProductRepository
        by lazy {
            CartProductRepositoryImpl(
                CartProductLocalDataSource(database.cartProductDao),
                productRepository,
            )
        }
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
