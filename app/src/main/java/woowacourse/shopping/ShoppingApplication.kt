package woowacourse.shopping

import android.app.Application
import okhttp3.OkHttpClient
import woowacourse.shopping.data.local.database.DataBase
import woowacourse.shopping.data.local.repository.PurchaseProductsRepository
import woowacourse.shopping.data.local.repository.RecentlyViewedProductRepository
import woowacourse.shopping.data.remote.mock.ProductWebServer
import woowacourse.shopping.data.remote.repository.ProductRepository

class ShoppingApplication : Application() {
    val database by lazy { DataBase.getDatabase(this) }

    val purchaseProductsRepository by lazy {
        PurchaseProductsRepository(database.purchaseProductsDao())
    }

    val recentlyViewedProductRepository by lazy {
        RecentlyViewedProductRepository(database.recentlyViewedProductDao())
    }

    val client by lazy { OkHttpClient() }
    val productRepository by lazy {
        ProductRepository(client, ProductWebServer.baseUrl)
    }

    override fun onCreate() {
        super.onCreate()
        ProductWebServer.start()
    }
}
