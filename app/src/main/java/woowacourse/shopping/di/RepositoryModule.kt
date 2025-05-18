package woowacourse.shopping.di

import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.repository.LocalProductRepository
import woowacourse.shopping.domain.repository.ProductRepository

object RepositoryModule {
    fun provideProductRepository(database: ShoppingDatabase): ProductRepository =
        LocalProductRepository(
            database.productDao(),
        )
}
