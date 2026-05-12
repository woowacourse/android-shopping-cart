package woowacourse.shopping.repository

import android.content.Context
import okhttp3.OkHttpClient
import woowacourse.shopping.local.ShoppingDatabase
import woowacourse.shopping.network.ConnectivityManagerNetworkMonitor
import woowacourse.shopping.network.NetworkMonitor
import woowacourse.shopping.repository.http.HttpProductRepository
import woowacourse.shopping.repository.http.ShoppingMockWebServer
import woowacourse.shopping.repository.room.RoomCartRepository
import woowacourse.shopping.repository.room.RoomRecentProductRepository

object ShoppingRepositoryProvider {
    private val httpClient: OkHttpClient = OkHttpClient()

    lateinit var productRepository: ProductRepository
        private set

    lateinit var cartRepository: CartRepository
        private set

    lateinit var recentProductRepository: RecentProductRepository
        private set

    lateinit var networkMonitor: NetworkMonitor
        private set

    fun initialize(context: Context) {
        if (
            ::productRepository.isInitialized &&
            ::cartRepository.isInitialized &&
            ::recentProductRepository.isInitialized &&
            ::networkMonitor.isInitialized
        ) {
            return
        }

        val database = ShoppingDatabase.getInstance(context)
        productRepository =
            HttpProductRepository(
                client = httpClient,
                baseUrlProvider = ShoppingMockWebServer::baseUrl,
            )
        cartRepository = RoomCartRepository(database.cartItemDao())
        recentProductRepository = RoomRecentProductRepository(database.recentProductDao())
        networkMonitor = ConnectivityManagerNetworkMonitor(context)
    }
}
