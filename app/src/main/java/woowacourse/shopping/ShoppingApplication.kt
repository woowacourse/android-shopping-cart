package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.ShoppingCartDatabase
import woowacourse.shopping.data.cart.LocalCartProductRepository
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.product.remote.ProductRemoteDataSource
import woowacourse.shopping.data.recent.LocalRecentProductRepository

class ShoppingApplication : Application() {
    private val database by lazy { ShoppingCartDatabase.getDataBase(this) }
    val productRepository by lazy { ProductRepositoryImpl(ProductRemoteDataSource()) }
    val cartProductRepository by lazy { LocalCartProductRepository(database.cartProductDao) }
    val recentProductRepository by lazy { LocalRecentProductRepository(database.recentProductDao) }
}
