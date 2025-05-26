package woowacourse.shopping.providers

import woowacourse.shopping.data.cart.CartDataSourceImpl
import woowacourse.shopping.data.product.ProductDataSourceImpl
import woowacourse.shopping.domain.cart.CartDataSource
import woowacourse.shopping.domain.product.ProductDataSource

object DataSourceProvider {
    fun provideProductDataSource(): ProductDataSource {
        return ProductDataSourceImpl(ClothesStoreDatabaseProvider.provideProductDao())
    }

    fun provideCartDataSource(): CartDataSource {
        return CartDataSourceImpl(ClothesStoreDatabaseProvider.provideCartDao())
    }
}
