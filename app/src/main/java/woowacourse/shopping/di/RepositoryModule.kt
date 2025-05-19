package woowacourse.shopping.di

import android.content.Context
import woowacourse.shopping.data.ProductRepositoryImpl
import woowacourse.shopping.domain.ProductRepository

object RepositoryModule {
    fun provideProductRepository(context: Context): ProductRepository {
        val db = DatabaseModule.provideDatabase(context)
        val cartDao = DatabaseModule.provideCartDao(db)
        return ProductRepositoryImpl(cartDao)
    }
}
