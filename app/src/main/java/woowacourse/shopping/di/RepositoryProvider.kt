package woowacourse.shopping.di

import woowacourse.shopping.data.repository.ShoppingRepositoryImpl
import woowacourse.shopping.domain.repository.ShoppingRepository

object RepositoryProvider {
    val shoppingRepository: ShoppingRepository by lazy { initShoppingRepository() }

    private fun initShoppingRepository(): ShoppingRepository {
        val productDataSource = DataSourceProvider.productDataSource
        val cartDataSource = DataSourceProvider.cartDataSource
        return ShoppingRepositoryImpl(productDataSource, cartDataSource)
    }
}
