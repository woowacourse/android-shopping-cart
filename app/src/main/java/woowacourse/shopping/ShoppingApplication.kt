package woowacourse.shopping

import android.app.Application
import okhttp3.OkHttpClient
import woowacourse.shopping.data.local.database.DataBase
import woowacourse.shopping.data.local.repository.PurchaseProductsRepositoryImpl
import woowacourse.shopping.data.local.repository.RecentlyViewedProductRepositoryImpl
import woowacourse.shopping.data.remote.mock.ProductWebServer
import woowacourse.shopping.data.remote.repository.ProductRepositoryImpl

class ShoppingApplication : Application() {
    val database by lazy { DataBase.getDatabase(this) }

    val purchaseProductsRepository by lazy {
        PurchaseProductsRepositoryImpl(database.purchaseProductsDao())
    }

    val recentlyViewedProductRepository by lazy {
        RecentlyViewedProductRepositoryImpl(database.recentlyViewedProductDao())
    }

    val client by lazy { OkHttpClient() }
    val productRepository by lazy {
        ProductRepositoryImpl(client, ProductWebServer.baseUrl)
    }

    override fun onCreate() {
        super.onCreate()
        ProductWebServer.start()
    }
}
