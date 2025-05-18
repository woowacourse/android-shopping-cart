package woowacourse.shopping.di

import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.domain.repository.ProductRepository

object RepositoryModule {
    fun provideProductRepository(database: ShoppingDatabase): ProductRepository =
        ProductRepositoryImpl(
            database.productDao(),
        )
}
