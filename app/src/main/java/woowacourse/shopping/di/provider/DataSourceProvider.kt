package woowacourse.shopping.di.provider

import okhttp3.OkHttpClient
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.datasource.CartDataSourceImpl
import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.datasource.ProductDataSourceImpl
import woowacourse.shopping.data.datasource.RecentProductLocalDataSource
import woowacourse.shopping.data.datasource.RecentProductLocalDataSourceImpl
import woowacourse.shopping.data.db.ShoppingDatabase
import woowacourse.shopping.data.service.MockProductService
import woowacourse.shopping.mockserver.MockServer

object DataSourceProvider {
    val productDataSource: ProductDataSource by lazy { initProductDataSource() }
    val cartDataSource: CartDataSource by lazy { initCartDataSource() }
    val recentProductLocalDataSource: RecentProductLocalDataSource by lazy { initRecentProductLocalDataSource() }

    private fun initProductDataSource(): ProductDataSource {
        val client = OkHttpClient()
        val mockServer = MockServer()
        val productService = MockProductService(client, mockServer)
        return ProductDataSourceImpl(productService)
    }

    private fun initCartDataSource(): CartDataSource {
        val database = ShoppingDatabase.getDatabase(ShoppingApplication.instance)
        val cartDao = database.cartDao()
        return CartDataSourceImpl(cartDao)
    }

    private fun initRecentProductLocalDataSource(): RecentProductLocalDataSource {
        val database = ShoppingDatabase.getDatabase(ShoppingApplication.instance)
        val recentProductDao = database.recentProductDao()
        return RecentProductLocalDataSourceImpl(recentProductDao)
    }
}
