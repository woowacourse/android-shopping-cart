package woowacourse.shopping.repository

import android.content.Context
import okhttp3.OkHttpClient
import woowacourse.shopping.BuildConfig
import woowacourse.shopping.local.ShoppingDatabase
import woowacourse.shopping.repository.http.HttpProductRepository
import woowacourse.shopping.repository.inmemory.InMemoryRecentProductRepository
import woowacourse.shopping.repository.network.ConnectivityManagerNetworkMonitor
import woowacourse.shopping.repository.network.NetworkMonitor
import woowacourse.shopping.repository.room.RoomCartRepository

object ShoppingRepositoryProvider {
    private val httpClient: OkHttpClient = OkHttpClient()

    val productRepository: ProductRepository =
        HttpProductRepository(
            client = httpClient,
            baseUrl = BuildConfig.PRODUCT_API_BASE_URL,
        )

    lateinit var cartRepository: CartRepository
        private set

    lateinit var recentProductRepository: RecentProductRepository
        private set

    lateinit var networkMonitor: NetworkMonitor
        private set

    fun initialize(context: Context) {
        if (::cartRepository.isInitialized && ::recentProductRepository.isInitialized && ::networkMonitor.isInitialized) return

        val database = ShoppingDatabase.getInstance(context)
        cartRepository = RoomCartRepository(database.cartItemDao())
        recentProductRepository = InMemoryRecentProductRepository
        networkMonitor = ConnectivityManagerNetworkMonitor(context)
    }
}
