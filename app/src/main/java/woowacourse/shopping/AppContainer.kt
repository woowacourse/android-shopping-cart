package woowacourse.shopping

import android.content.Context
import androidx.room.Room
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import woowacourse.shopping.data.local.db.ShoppingDatabase
import woowacourse.shopping.data.remote.product.ProductHttpClient
import woowacourse.shopping.network.AndroidNetworkMonitor
import woowacourse.shopping.network.NetworkMonitor
import woowacourse.shopping.repository.cart.CartRepository
import woowacourse.shopping.repository.cart.RoomCartRepository
import woowacourse.shopping.repository.product.ProductRepository
import woowacourse.shopping.repository.product.RoomProductRepository
import woowacourse.shopping.repository.recentviewedproduct.RecentlyViewedProductsRepository
import woowacourse.shopping.repository.recentviewedproduct.RoomRecentlyViewedProductsRepository

object AppContainer {
    private lateinit var appContext: Context
    private lateinit var database: ShoppingDatabase

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient()
    }

    private val json: Json by lazy {
        Json {
            ignoreUnknownKeys = true
        }
    }

    private val baseUrl = BuildConfig.BASE_URL

    fun initialize(context: Context) {
        appContext = context.applicationContext
        database =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    ShoppingDatabase::class.java,
                    "shopping.db",
                ).fallbackToDestructiveMigration(dropAllTables = true)
                .build()
    }

    val productRepository: ProductRepository by lazy {
        RoomProductRepository(
            productDao = database.productDao(),
            productHttpClient =
                ProductHttpClient(
                    baseUrl = baseUrl,
                    okHttpClient = okHttpClient,
                    json = json,
                ),
        )
    }

    val networkMonitor: NetworkMonitor by lazy {
        AndroidNetworkMonitor(appContext)
    }

    val cartRepository: CartRepository by lazy {
        RoomCartRepository(
            cartDao = database.cartDao(),
        )
    }

    val recentlyViewedProductsRepository: RecentlyViewedProductsRepository by lazy {
        RoomRecentlyViewedProductsRepository(
            recentlyViewedProductsDao = database.recentlyViewedProductsDao(),
            productsDao = database.productDao(),
        )
    }
}
