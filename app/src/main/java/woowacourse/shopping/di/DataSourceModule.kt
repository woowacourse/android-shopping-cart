package woowacourse.shopping.di

import android.content.Context
import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.datasource.CartDataSourceImpl
import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.datasource.ProductDataSourceImpl

object DataSourceModule {
    private var cartDataSource: CartDataSource? = null
    private var productDataSource: ProductDataSource? = null

    fun provideProductDataSource(): ProductDataSource =
        productDataSource ?: run {
            ProductDataSourceImpl().also { productDataSource = it }
        }

    fun provideCartDataSource(context: Context): CartDataSource =
        cartDataSource ?: run {
            val db = DatabaseModule.provideDatabase(context)
            val cartDao = DatabaseModule.provideCartDao(db)
            CartDataSourceImpl(cartDao).also { cartDataSource = it }
        }
}
