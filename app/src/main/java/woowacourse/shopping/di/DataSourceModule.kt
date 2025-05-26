package woowacourse.shopping.di

import android.content.Context
import woowacourse.shopping.data.datasource.local.CartDataSource
import woowacourse.shopping.data.datasource.local.CartDataSourceImpl
import woowacourse.shopping.data.datasource.local.RecentProductDataSource
import woowacourse.shopping.data.datasource.local.RecentProductDataSourceImpl
import woowacourse.shopping.data.datasource.remote.ProductDataSource
import woowacourse.shopping.data.datasource.remote.ProductDataSourceImpl

object DataSourceModule {
    private var cartDataSource: CartDataSource? = null
    private var productDataSource: ProductDataSource? = null
    private var recentProductDataSource: RecentProductDataSource? = null
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    fun provideProductDataSource(): ProductDataSource =
        productDataSource ?: run {
            val productService = NetworkModule.provideProductService()
            ProductDataSourceImpl(productService).also { productDataSource = it }
        }

    fun provideCartDataSource(): CartDataSource =
        cartDataSource ?: run {
            val cartDao = DatabaseModule.provideCartDao()
            CartDataSourceImpl(cartDao).also { cartDataSource = it }
        }

    fun provideRecentProductDataSource(): RecentProductDataSource =
        recentProductDataSource ?: run {
            val recentProductDao = DatabaseModule.provideRecentProductDao()
            RecentProductDataSourceImpl(recentProductDao).also { recentProductDataSource = it }
        }
}
