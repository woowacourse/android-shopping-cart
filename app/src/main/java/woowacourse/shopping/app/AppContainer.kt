package woowacourse.shopping.app

import android.content.Context
import androidx.room.Room
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import woowacourse.shopping.data.local.database.ShoppingDatabase
import woowacourse.shopping.data.network.ConnectivityManagerNetworkMonitor
import woowacourse.shopping.data.network.NetworkMonitor
import woowacourse.shopping.data.remote.datasource.CartRemoteDataSource
import woowacourse.shopping.data.remote.datasource.ProductRemoteDataSource
import woowacourse.shopping.data.remote.datasource.okhttp.OkHttpCartRemoteDataSource
import woowacourse.shopping.data.remote.datasource.okhttp.OkHttpProductRemoteDataSource
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.repository.RecentlyViewedProductRepositoryImpl
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentlyViewedProductRepository

object AppContainer {
    private lateinit var database: ShoppingDatabase

    private val okHttpClient: OkHttpClient =
        OkHttpClient
            .Builder()
            .build()

    private val json: Json =
        Json {
            ignoreUnknownKeys = true
        }

    lateinit var productRemoteDataSource: ProductRemoteDataSource
        private set

    lateinit var cartRemoteDataSource: CartRemoteDataSource
        private set

    lateinit var productRepository: ProductRepository
        private set

    lateinit var cartRepository: CartRepository
        private set

    lateinit var recentlyViewedProductRepository: RecentlyViewedProductRepository
        private set

    lateinit var networkMonitor: NetworkMonitor
        private set

    fun initialize(
        context: Context,
        baseUrl: String,
    ) {
        database =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    ShoppingDatabase::class.java,
                    "shopping-db",
                ).fallbackToDestructiveMigration(dropAllTables = true)
                .build()

        productRemoteDataSource =
            OkHttpProductRemoteDataSource(
                client = okHttpClient,
                baseUrl = baseUrl,
                json = json,
            )

        cartRemoteDataSource =
            OkHttpCartRemoteDataSource(
                client = okHttpClient,
                baseUrl = baseUrl,
                json = json,
            )

        productRepository =
            ProductRepositoryImpl(
                productRemoteDataSource = productRemoteDataSource,
            )

        cartRepository =
            CartRepositoryImpl(
                cartDao = database.cartDao(),
                productRepository = productRepository,
            )

        recentlyViewedProductRepository =
            RecentlyViewedProductRepositoryImpl(
                dao = database.recentlyViewedProductDao(),
            )

        networkMonitor = ConnectivityManagerNetworkMonitor(context)
    }
}
