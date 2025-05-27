package woowacourse.shopping.providers

import CartRepositoryImpl
import woowacourse.shopping.data.product.ProductOverViewRepositoryImpl
import woowacourse.shopping.domain.cart.CartRepository
import woowacourse.shopping.domain.product.ProductOverViewRepository

object RepositoryProvider {
    fun provideCartRepository(): CartRepository {
        return CartRepositoryImpl(
            DataSourceProvider.provideCartDataSource(),
        )
    }

    fun provideProductOverViewRepository(): ProductOverViewRepository {
        return ProductOverViewRepositoryImpl(
            DataSourceProvider.provideProductDataSource(),
            DataSourceProvider.provideCartDataSource(),
        )
    }
}
