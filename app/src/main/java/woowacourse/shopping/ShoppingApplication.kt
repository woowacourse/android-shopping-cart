package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.repository.RecentProductRepositoryImpl
import woowacourse.shopping.data.repository.ShoppingRepositoryImpl
import woowacourse.shopping.di.DataSourceProvider
import woowacourse.shopping.di.RepositoryProvider

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        _instance = this
        initRepository()
    }

    private fun initRepository() {
        initShoppingRepository()
        initRecentProductRepository()
    }

    private fun initShoppingRepository() {
        val productDataSource = DataSourceProvider.productDataSource
        val cartDataSource = DataSourceProvider.cartDataSource
        val repository = ShoppingRepositoryImpl(productDataSource, cartDataSource)
        RepositoryProvider.initShoppingRepository(repository)
    }

    private fun initRecentProductRepository() {
        val productDataSource = DataSourceProvider.productDataSource
        val recentProductLocalDataSource = DataSourceProvider.recentProductLocalDataSource
        val repository =
            RecentProductRepositoryImpl(productDataSource, recentProductLocalDataSource)
        RepositoryProvider.initRecentProductRepository(repository)
    }

    companion object {
        private var _instance: ShoppingApplication? = null
        val instance get() = _instance!!
    }
}
