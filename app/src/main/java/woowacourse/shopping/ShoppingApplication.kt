package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.api.NetworkModule
import woowacourse.shopping.data.db.ProductHistoryDatabase
import woowacourse.shopping.data.db.ShoppingCartDatabase
import woowacourse.shopping.data.repsoitory.local.ProductHistoryRepositoryImpl
import woowacourse.shopping.data.repsoitory.local.ShoppingCartRepositoryImpl
import woowacourse.shopping.data.repsoitory.remote.ProductRepositoryImpl
import woowacourse.shopping.domain.repository.local.ProductHistoryRepository
import woowacourse.shopping.domain.repository.local.ShoppingCartRepository
import woowacourse.shopping.domain.repository.remote.ProductRepository

class ShoppingApplication : Application() {
    val shoppingCartRepository: ShoppingCartRepository by lazy {
        ShoppingCartRepositoryImpl(ShoppingCartDatabase.getDatabase(applicationContext).dao())
    }
    val productHistoryRepository: ProductHistoryRepository by lazy {
        ProductHistoryRepositoryImpl(ProductHistoryDatabase.getDatabase(applicationContext).dao())
    }
    val productRepository: ProductRepository by lazy { ProductRepositoryImpl(NetworkModule()) }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onTerminate() {
        super.onTerminate()
        productRepository.shutdown()
    }
}
