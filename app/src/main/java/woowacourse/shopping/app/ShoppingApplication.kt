package woowacourse.shopping.app

import android.app.Application
import woowacourse.shopping.data.datasource.local.ProductHistoryDataSource
import woowacourse.shopping.data.datasource.local.ShoppingCartDataSource
import woowacourse.shopping.data.datasource.remote.ProductDataSource
import woowacourse.shopping.data.repsoitory.ProductHistoryRepositoryImpl
import woowacourse.shopping.data.repsoitory.ProductRepositoryImpl
import woowacourse.shopping.data.repsoitory.ShoppingCartRepositoryImpl
import woowacourse.shopping.domain.repository.ProductHistoryRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.local.datasource.ProductHistoryDataSourceImpl
import woowacourse.shopping.local.datasource.ShoppingCartDataSourceImpl
import woowacourse.shopping.local.db.ProductHistoryDatabase
import woowacourse.shopping.local.db.ShoppingCartDatabase
import woowacourse.shopping.remote.api.NetworkModule
import woowacourse.shopping.remote.datasource.ProductDataSourceImpl

class ShoppingApplication : Application() {
    private val shoppingCartDataSource: ShoppingCartDataSource by lazy {
        ShoppingCartDataSourceImpl(ShoppingCartDatabase.getDatabase(applicationContext).dao())
    }
    val shoppingCartRepository: ShoppingCartRepository by lazy {
        ShoppingCartRepositoryImpl(shoppingCartDataSource)
    }

    private val productHistoryDataSource: ProductHistoryDataSource by lazy {
        ProductHistoryDataSourceImpl(ProductHistoryDatabase.getDatabase(applicationContext).dao())
    }
    val productHistoryRepository: ProductHistoryRepository by lazy {
        ProductHistoryRepositoryImpl(productHistoryDataSource)
    }

    private val productDataSource: ProductDataSource by lazy { ProductDataSourceImpl(NetworkModule()) }
    val productRepository: ProductRepository by lazy {
        ProductRepositoryImpl(
            productDataSource,
            shoppingCartDataSource,
        )
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onTerminate() {
        super.onTerminate()
        productRepository.shutdown()
    }
}
