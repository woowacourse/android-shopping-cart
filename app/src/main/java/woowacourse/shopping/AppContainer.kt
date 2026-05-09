package woowacourse.shopping

import android.content.Context
import androidx.room.Room
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import woowacourse.shopping.data.local.db.ShoppingDatabase
import woowacourse.shopping.data.local.mapper.toEntity
import woowacourse.shopping.data.remote.product.ProductHttpClient
import woowacourse.shopping.repository.cart.CartRepository
import woowacourse.shopping.repository.cart.RoomCartRepository
import woowacourse.shopping.repository.product.ProductRepository
import woowacourse.shopping.repository.product.RoomProductRepository
import woowacourse.shopping.repository.recentviewedproduct.RecentlyViewedProductsRepository
import woowacourse.shopping.repository.recentviewedproduct.RoomRecentlyViewedProductsRepository

object AppContainer {
    private lateinit var database: ShoppingDatabase

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient()
    }

    private val json: Json by lazy {
        Json {
            ignoreUnknownKeys = true
        }
    }

    private const val BASE_URL = "http://10.0.2.2:12345/"
    fun initialize(context: Context) {
        database =
            Room.databaseBuilder(
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
                    baseUrl = BASE_URL,
                    okHttpClient = okHttpClient,
                    json = json,
                ),
        )
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
