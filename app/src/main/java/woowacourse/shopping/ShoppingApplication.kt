package woowacourse.shopping

import android.app.Application
import okhttp3.OkHttpClient
import woowacourse.shopping.data.local.database.DataBase
import woowacourse.shopping.data.local.repository.PurchaseProductsRepository
import woowacourse.shopping.data.local.repository.RecentlyViewedProductRepository
import woowacourse.shopping.data.remote.mock.MockWebServer
import woowacourse.shopping.data.remote.repository.WebServerRepository

class ShoppingApplication : Application() {
    val database by lazy { DataBase.getDatabase(this) }

    val purchaseProductsRepository by lazy {
        PurchaseProductsRepository(database.purchaseProductsDao())
    }

    val recentlyViewedProductRepository by lazy {
        RecentlyViewedProductRepository(database.recentlyViewedProductDao())
    }

    val client by lazy { OkHttpClient() }
    val webServerRepository by lazy {
        WebServerRepository(client, MockWebServer.baseUrl)
    }

    override fun onCreate() {
        super.onCreate()
        Thread {
            MockWebServer.start()
        }
    }
}
