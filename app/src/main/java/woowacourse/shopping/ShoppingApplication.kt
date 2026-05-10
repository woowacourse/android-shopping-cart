package woowacourse.shopping

import android.app.Application
import androidx.room.Room
import woowacourse.shopping.repository.AndroidShoppingDatabase
import woowacourse.shopping.repository.DatabaseProductRepository
import woowacourse.shopping.repository.DatabaseShoppingCartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.ShoppingCartRepository

class ShoppingApplication : Application() {
    private val database by lazy {
        Room
            .databaseBuilder(
                applicationContext,
                AndroidShoppingDatabase::class.java,
                if (BuildConfig.DEBUG) "debug-shopping-db" else "release-shopping-db",
            ).apply {
                if (BuildConfig.DEBUG) {
                    createFromAsset("shopping-cart.db")
                }
            }.build()
    }

    val productRepository: ProductRepository by lazy {
        DatabaseProductRepository(database.productDao())
    }

    val shoppingCartRepository: ShoppingCartRepository by lazy {
        DatabaseShoppingCartRepository(productRepository, database.shoppingCartItemDao())
    }
}
