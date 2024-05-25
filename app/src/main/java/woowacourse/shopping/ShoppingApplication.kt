package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.remote.MockShoppingWebServer
import woowacourse.shopping.data.repository.cart.CartRepositoryImpl
import woowacourse.shopping.data.repository.history.ProductHistoryRepositoryImpl
import woowacourse.shopping.data.repository.product.ProductRepositoryImpl

class ShoppingApplication : Application() {
    val database by lazy { ShoppingDatabase.getDatabase(this) }
    val productRepository by lazy { ProductRepositoryImpl() }
    val cartRepository by lazy { CartRepositoryImpl() }
    val productHistoryRepository by lazy { ProductHistoryRepositoryImpl(database.productHistoryDao()) }

    override fun onCreate() {
        super.onCreate()
        MockShoppingWebServer(database)
    }
}
