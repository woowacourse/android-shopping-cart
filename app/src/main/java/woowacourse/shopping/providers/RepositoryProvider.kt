package woowacourse.shopping.providers

import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.domain.cart.CartRepository
import woowacourse.shopping.domain.product.ProductRepository

object RepositoryProvider {

    fun provideCartRepository(): CartRepository {
        return CartRepositoryImpl(
            ClothesStoreDatabaseProvider.provideCartDao()
        )
    }

    fun provideProductRepository(): ProductRepository {
        return ProductRepositoryImpl(
            ClothesStoreDatabaseProvider.provideProductDao()
        )
    }
}