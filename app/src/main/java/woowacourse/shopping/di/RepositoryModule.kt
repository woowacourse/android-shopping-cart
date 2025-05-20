package woowacourse.shopping.di

import android.content.Context
import woowacourse.shopping.data.ProductRepositoryImpl
import woowacourse.shopping.domain.ProductRepository

object RepositoryModule {
    private var productRepository: ProductRepository? = null

    fun provideProductRepository(context: Context): ProductRepository =
        productRepository ?: run {
            val db = DatabaseModule.provideDatabase(context)
            val cartDao = DatabaseModule.provideCartDao(db)
            ProductRepositoryImpl(cartDao).also {
                productRepository = it
            }
        }
}
