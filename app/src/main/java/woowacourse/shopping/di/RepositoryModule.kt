package woowacourse.shopping.di

import android.content.Context
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

object RepositoryModule {
    private var productRepository: ProductRepository? = null
    private var cartRepository: CartRepository? = null

    fun provideProductRepository(context: Context): ProductRepository =
        productRepository ?: run {
            val cartDataSource = DataSourceModule.provideCartDataSource(context)
            val productDataSource = DataSourceModule.provideProductDataSource()
            ProductRepositoryImpl(cartDataSource, productDataSource).also {
                productRepository = it
            }
        }

    fun provideCartRepository(context: Context): CartRepository =
        cartRepository ?: run {
            val cartDataSource = DataSourceModule.provideCartDataSource(context)
            CartRepositoryImpl(cartDataSource).also {
                cartRepository = it
            }
        }
}
