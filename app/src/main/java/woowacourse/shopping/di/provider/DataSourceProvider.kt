package woowacourse.shopping.di.provider

import okhttp3.OkHttpClient
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.datasource.CartLocalDataSource
import woowacourse.shopping.data.datasource.CartLocalDataSourceImpl
import woowacourse.shopping.data.datasource.ProductRemoteDataSource
import woowacourse.shopping.data.datasource.ProductRemoteDataSourceImpl
import woowacourse.shopping.data.datasource.RecentProductLocalDataSource
import woowacourse.shopping.data.datasource.RecentProductLocalDataSourceImpl
import woowacourse.shopping.data.db.ShoppingDatabase
import woowacourse.shopping.data.service.MockProductService
import woowacourse.shopping.mockserver.MockServer

object DataSourceProvider {
    val productRemoteDataSource: ProductRemoteDataSource by lazy { initProductDataSource() }
    val cartLocalDataSource: CartLocalDataSource by lazy { initCartDataSource() }
    val recentProductLocalDataSource: RecentProductLocalDataSource by lazy { initRecentProductLocalDataSource() }

    private fun initProductDataSource(): ProductRemoteDataSource {
        val client = OkHttpClient()
        val mockServer = MockServer()
        val productService = MockProductService(client, mockServer)
        return ProductRemoteDataSourceImpl(productService)
    }

    private fun initCartDataSource(): CartLocalDataSource {
        val database = ShoppingDatabase.getDatabase(ShoppingApplication.instance)
        val cartDao = database.cartDao()
        return CartLocalDataSourceImpl(cartDao)
    }

    private fun initRecentProductLocalDataSource(): RecentProductLocalDataSource {
        val database = ShoppingDatabase.getDatabase(ShoppingApplication.instance)
        val recentProductDao = database.recentProductDao()
        return RecentProductLocalDataSourceImpl(recentProductDao)
    }
}
