package woowacourse.shopping.di

import android.content.Context
import androidx.room.Room
import woowacourse.shopping.data.local.ShoppingDatabase
import woowacourse.shopping.data.remote.HttpClientProvider
import woowacourse.shopping.data.remote.MockWebServerProvider
import woowacourse.shopping.data.remote.api.ProductService
import woowacourse.shopping.data.remote.api.ProductServiceImpl
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.data.repository.cart.RoomCartRepository
import woowacourse.shopping.data.repository.product.RemoteProductRepository
import woowacourse.shopping.data.repository.recent.RoomRecentProductRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

object DataContainer {
    private var appContext: Context? = null
    fun init(context: Context) {
        if (appContext == null) {
            appContext = context.applicationContext
        }
    }

    private val database: ShoppingDatabase by lazy {
        Room.databaseBuilder(
            requireNotNull(appContext) { "DataContainer.init(context) must be called first." },
            ShoppingDatabase::class.java,
            "shopping.db",
        ).fallbackToDestructiveMigration(dropAllTables = true).build()
    }

    val cartRepository: CartRepository by lazy { RoomCartRepository(database.cartItemDao()) }
    val productRepository: ProductRepository by lazy { RemoteProductRepository(productService) }
    val recentProductRepository: RecentProductRepository by lazy {
        RoomRecentProductRepository(database.recentProductDao())
    }

    val productService: ProductService by lazy {
        ProductServiceImpl(
            client = HttpClientProvider.okHttpClient,
            baseUrlProvider = { MockWebServerProvider.baseUrl },
        )
    }
}
