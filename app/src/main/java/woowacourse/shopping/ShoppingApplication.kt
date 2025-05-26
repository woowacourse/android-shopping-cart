package woowacourse.shopping

import android.app.Application
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.repository.RecentProductRepositoryImpl
import woowacourse.shopping.di.provider.DataSourceProvider
import woowacourse.shopping.di.provider.RepositoryProvider

class ShoppingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        _instance = this
        initRepositories()
    }

    private fun initRepositories() {
        initProductRepository()
        initCartRepository()
        initRecentProductRepository()
    }

    private fun initProductRepository() {
        val productDataSource = DataSourceProvider.productRemoteDataSource
        val repository = ProductRepositoryImpl(productDataSource)
        RepositoryProvider.initProductRepository(repository)
    }

    private fun initCartRepository() {
        val cartDataSource = DataSourceProvider.cartLocalDataSource
        val productDataSource = DataSourceProvider.productRemoteDataSource
        val repository = CartRepositoryImpl(cartDataSource, productDataSource)
        RepositoryProvider.initCartRepository(repository)
    }

    private fun initRecentProductRepository() {
        val productDataSource = DataSourceProvider.productRemoteDataSource
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
