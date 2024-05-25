package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.remote.MockShoppingWebServer
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductHistoryRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl

class ShoppingApplication : Application() {
    private val database by lazy { ShoppingDatabase.getDatabase(this) }
    private val server by lazy { MockShoppingWebServer(database.productDao(), database.cartDao()) }
    val productRepository by lazy { ProductRepositoryImpl(server) }
    val cartRepository by lazy { CartRepositoryImpl(server) }
    val productHistoryRepository by lazy { ProductHistoryRepositoryImpl(database.productHistoryDao()) }
}
