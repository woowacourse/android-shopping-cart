package woowacourse.shopping.di

import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.repository.RecentProductRepositoryImpl
import woowacourse.shopping.di.provider.DataSourceProvider
import woowacourse.shopping.di.provider.RepositoryProvider
import woowacourse.shopping.di.provider.RepositoryProviderInterface

class RepositoryInitializer(
    private val repositoryProvider: RepositoryProviderInterface = RepositoryProvider,
    private val dataSourceProvider: DataSourceProvider = DataSourceProvider,
) {
    fun init() {
        initProductRepository()
        initCartRepository()
        initRecentProductRepository()
    }

    private fun initProductRepository() {
        val productDataSource = dataSourceProvider.productRemoteDataSource
        val repository = ProductRepositoryImpl(productDataSource)
        repositoryProvider.initProductRepository(repository)
    }

    private fun initCartRepository() {
        val cartDataSource = dataSourceProvider.cartLocalDataSource
        val productDataSource = dataSourceProvider.productRemoteDataSource
        val repository = CartRepositoryImpl(cartDataSource, productDataSource)
        repositoryProvider.initCartRepository(repository)
    }

    private fun initRecentProductRepository() {
        val productDataSource = dataSourceProvider.productRemoteDataSource
        val recentProductLocalDataSource = dataSourceProvider.recentProductLocalDataSource
        val repository =
            RecentProductRepositoryImpl(productDataSource, recentProductLocalDataSource)
        repositoryProvider.initRecentProductRepository(repository)
    }
}
