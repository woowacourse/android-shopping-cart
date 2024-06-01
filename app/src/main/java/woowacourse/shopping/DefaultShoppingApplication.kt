package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.local.ProductHistoryLocalDataSource
import woowacourse.shopping.data.remote.CartRemoteDataSource
import woowacourse.shopping.data.remote.MockShoppingWebServer
import woowacourse.shopping.data.remote.ProductRemoteDataSource
import woowacourse.shopping.data.repository.cart.CartRepositoryImpl
import woowacourse.shopping.data.repository.product.ProductRepositoryImpl
import woowacourse.shopping.domain.repository.cart.CartRepository
import woowacourse.shopping.domain.repository.product.ProductRepository
import woowacourse.shopping.presentation.cart.CartViewModelFactory
import woowacourse.shopping.presentation.home.HomeViewModelFactory

class DefaultShoppingApplication : Application(), ShoppingApplication {
    private val database by lazy { ShoppingDatabase.getDatabase(this) }

    override val productRepository by lazy {
        ProductRepositoryImpl(
            ProductRemoteDataSource(),
            ProductHistoryLocalDataSource(database.productHistoryDao()),
        )
    }
    override val cartRepository by lazy { CartRepositoryImpl(CartRemoteDataSource()) }

    override val cartViewModelFactory by lazy {
        CartViewModelFactory(
            cartRepository,
            productRepository,
        )
    }

    override val homeViewModelFactory by lazy {
        HomeViewModelFactory(
            productRepository,
            cartRepository,
        )
    }

    override fun onCreate() {
        super.onCreate()
        MockShoppingWebServer(database)
    }
}
