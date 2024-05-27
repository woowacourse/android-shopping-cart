package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.local.ProductHistoryLocalDataSource
import woowacourse.shopping.data.remote.CartRemoteDataSource
import woowacourse.shopping.data.remote.MockShoppingWebServer
import woowacourse.shopping.data.remote.ProductRemoteDataSource
import woowacourse.shopping.data.repository.cart.CartRepositoryImpl
import woowacourse.shopping.data.repository.product.ProductRepositoryImpl

class ShoppingApplication : Application() {
    val database by lazy { ShoppingDatabase.getDatabase(this) }
    val productRepository by lazy {
        ProductRepositoryImpl(
            ProductRemoteDataSource(),
            ProductHistoryLocalDataSource(database.productHistoryDao())
        )
    }
    val cartRepository by lazy { CartRepositoryImpl(CartRemoteDataSource()) }

    override fun onCreate() {
        super.onCreate()
        MockShoppingWebServer(database)
    }
}
