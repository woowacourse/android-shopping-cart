package woowacourse.shopping

import android.app.Application
import androidx.room.Room
import woowacourse.shopping.repository.AndroidShoppingDatabase
import woowacourse.shopping.repository.DefaultProductRepository
import woowacourse.shopping.repository.DefaultShoppingCartRepository
import woowacourse.shopping.repository.DefaultViewedProductRepository
import woowacourse.shopping.repository.ProductRemoteDataSource
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.ShoppingCartRepository
import woowacourse.shopping.repository.ViewedProductRepository

class ShoppingApplication : Application() {
    private val mockWebServer by lazy { startMockWebServer() }

    private val database by lazy {
        Room
            .databaseBuilder(
                applicationContext,
                AndroidShoppingDatabase::class.java,
                "shopping-db",
            ).addMigrations(AndroidShoppingDatabase.MIGRATION_1_2)
            .build()
    }

    val productRepository: ProductRepository by lazy {
        DefaultProductRepository(
            ProductRemoteDataSource("http://127.0.0.1:12345"),
        )
    }

    val shoppingCartRepository: ShoppingCartRepository by lazy {
        DefaultShoppingCartRepository(productRepository, database.shoppingCartItemDao())
    }

    val viewedProductRepository: ViewedProductRepository by lazy {
        DefaultViewedProductRepository(productRepository, database.viewedProductDao())
    }

    override fun onCreate() {
        super.onCreate()
        Thread {
            mockWebServer.start(12345)
        }.start()
    }
}
