package woowacourse.shopping.di

import android.content.Context
import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.datasource.CartDataSourceImpl
import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.datasource.ProductDataSourceImpl
import woowacourse.shopping.data.datasource.RecentProductDataSource
import woowacourse.shopping.data.datasource.RecentProductDataSourceImpl

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
            val db = DatabaseModule.provideDatabase()
            val cartDao = DatabaseModule.provideCartDao()
            CartDataSourceImpl(cartDao).also { cartDataSource = it }
        }

    fun provideRecentProductDataSource(): RecentProductDataSource =
        recentProductDataSource ?: run {
            val db = DatabaseModule.provideDatabase()
            val recentProductDao = DatabaseModule.provideRecentProductDao()
            RecentProductDataSourceImpl(recentProductDao).also { recentProductDataSource = it }
        }
}
