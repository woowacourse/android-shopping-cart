package woowacourse.shopping.data

import android.content.Context
import woowacourse.shopping.data.source.local.CartDatabase
import woowacourse.shopping.data.source.local.repositoryImpl.cart.CartRepositoryRoomImpl
import woowacourse.shopping.data.source.local.repositoryImpl.recentProduct.RecentProductRepositoryRoomImpl
import woowacourse.shopping.data.source.remote.ProductRemoteDataSource
import woowacourse.shopping.data.source.remote.ProductRepositoryRemoteImpl
import woowacourse.shopping.data.source.remote.ShoppingMockServer
import woowacourse.shopping.domain.RecentProductRepository
import woowacourse.shopping.domain.cart.repository.CartRepository

object DataProvider {
    private var networkMonitor: NetworkMonitor? = null

    fun getNetworkMonitor(context: Context): NetworkMonitor = networkMonitor ?: NetworkMonitor(context).also { networkMonitor = it }

    private val httpClient by lazy { okhttp3.OkHttpClient() }
    private val remoteDataSource by lazy {
        ProductRemoteDataSource(httpClient, ShoppingMockServer.baseUrl)
    }
    val productRepository by lazy {
        ProductRepositoryRemoteImpl(remoteDataSource)
    }
    private var cartRepository: CartRepository? = null
    private var recentProductRepository: RecentProductRepository? = null

    fun getCartRepository(context: Context): CartRepository =
        cartRepository ?: CartRepositoryRoomImpl(
            CartDatabase.getDatabase(context).cartDao(),
            productRepository,
        ).also { cartRepository = it }

    fun getRecentProductRepository(context: Context): RecentProductRepository =
        recentProductRepository ?: RecentProductRepositoryRoomImpl(
            CartDatabase.getDatabase(context).recentProductDao(),
            productRepository,
        ).also { recentProductRepository = it }
}
