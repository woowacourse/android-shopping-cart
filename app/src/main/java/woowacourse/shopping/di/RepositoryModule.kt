package woowacourse.shopping.di

import android.content.Context
import woowacourse.shopping.data.CartRepositoryImpl
import woowacourse.shopping.data.ProductRepositoryImpl
import woowacourse.shopping.data.datasource.CartDataSourceImpl
import woowacourse.shopping.data.datasource.ProductDataSourceImpl
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.ProductRepository

object RepositoryModule {
    private var productRepository: ProductRepository? = null
    private var cartRepository: CartRepository? = null

    fun provideProductRepository(context: Context): ProductRepository =
        productRepository ?: run {
            val db = DatabaseModule.provideDatabase(context)
            val cartDao = DatabaseModule.provideCartDao(db)
            val cartDataSource = CartDataSourceImpl(cartDao)
            val productDataSource = ProductDataSourceImpl()
            ProductRepositoryImpl(cartDataSource, productDataSource).also {
                productRepository = it
            }
        }

    fun provideCartRepository(context: Context): CartRepository =
        cartRepository ?: run {
            val db = DatabaseModule.provideDatabase(context)
            val cartDao = DatabaseModule.provideCartDao(db)
            val cartDataSource = CartDataSourceImpl(cartDao)
            CartRepositoryImpl(cartDataSource).also {
                cartRepository = it
            }
        }
}
