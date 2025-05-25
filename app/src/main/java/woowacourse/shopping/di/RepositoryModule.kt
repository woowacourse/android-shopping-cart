package woowacourse.shopping.di

import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.repository.RecentProductRepositoryImpl
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

object RepositoryModule {
    private var productRepository: ProductRepository? = null
    private var cartRepository: CartRepository? = null
    private var recentProductRepository: RecentProductRepository? = null

    fun provideProductRepository(): ProductRepository =
        productRepository ?: run {
            val cartDataSource = DataSourceModule.provideCartDataSource()
            val productDataSource = DataSourceModule.provideProductDataSource()
            ProductRepositoryImpl(cartDataSource, productDataSource).also {
                productRepository = it
            }
        }

    fun provideCartRepository(): CartRepository =
        cartRepository ?: run {
            val cartDataSource = DataSourceModule.provideCartDataSource()
            CartRepositoryImpl(cartDataSource).also { cartRepository = it }
        }

    fun provideRecentProductRepository(): RecentProductRepository =
        recentProductRepository ?: run {
            val recentlyProductDataSource = DataSourceModule.provideRecentProductDataSource()
            val productDataSource = DataSourceModule.provideProductDataSource()
            RecentProductRepositoryImpl(recentlyProductDataSource, productDataSource).also {
                recentProductRepository = it
            }
        }
}
