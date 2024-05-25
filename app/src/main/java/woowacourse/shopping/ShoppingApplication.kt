package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.api.NetworkModule
import woowacourse.shopping.data.datasource.local.ProductHistoryDataSource
import woowacourse.shopping.data.datasource.local.ShoppingCartDataSource
import woowacourse.shopping.data.repsoitory.local.ProductHistoryRepositoryImpl
import woowacourse.shopping.data.repsoitory.local.ShoppingCartRepositoryImpl
import woowacourse.shopping.data.repsoitory.remote.ProductRepositoryImpl
import woowacourse.shopping.domain.repository.local.ProductHistoryRepository
import woowacourse.shopping.domain.repository.local.ShoppingCartRepository
import woowacourse.shopping.domain.repository.remote.ProductRepository
import woowacourse.shopping.local.datasource.ProductHistoryDataSourceImpl
import woowacourse.shopping.local.datasource.ShoppingCartDataSourceImpl
import woowacourse.shopping.local.db.ProductHistoryDatabase
import woowacourse.shopping.local.db.ShoppingCartDatabase

class ShoppingApplication : Application() {
    private val shoppingCartDataSource: ShoppingCartDataSource by lazy {
        ShoppingCartDataSourceImpl(ShoppingCartDatabase.getDatabase(applicationContext).dao())
    }
    private val productHistoryDataSource: ProductHistoryDataSource by lazy {
        ProductHistoryDataSourceImpl(ProductHistoryDatabase.getDatabase(applicationContext).dao())
    }

    val shoppingCartRepository: ShoppingCartRepository by lazy {
        ShoppingCartRepositoryImpl(shoppingCartDataSource)
    }
    val productHistoryRepository: ProductHistoryRepository by lazy {
        ProductHistoryRepositoryImpl(productHistoryDataSource)
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
