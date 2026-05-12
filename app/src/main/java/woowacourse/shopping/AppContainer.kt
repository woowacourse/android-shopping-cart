package woowacourse.shopping

import android.content.Context
import okhttp3.OkHttpClient
import woowacourse.shopping.data.localdb.ShoppingDB
import woowacourse.shopping.data.network.HttpProductServer
import woowacourse.shopping.data.network.NetworkManager
import woowacourse.shopping.data.network.NetworkObserver
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.HttpProductRepository
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentItemRepository
import java.util.concurrent.TimeUnit

class AppContainer(
    context: Context,
) {
    private val database = ShoppingDB.getInstance(context)

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient
            .Builder()
            .callTimeout(10L, TimeUnit.SECONDS)
            .connectTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(10L, TimeUnit.SECONDS)
            .writeTimeout(10L, TimeUnit.SECONDS)
            .build()
    }

    val productRepository: ProductRepository by lazy {
        HttpProductRepository(
            baseUrl = HttpProductServer.baseUrl,
            client = okHttpClient,
        )
    }

    val cartRepository: CartRepository by lazy {
        CartRepository(database.cartItemDao())
    }

    val recentItemRepository: RecentItemRepository by lazy {
        RecentItemRepository(database.recentItemDao(), productRepository)
    }

    val networkObserver: NetworkObserver by lazy {
        NetworkManager(context)
    }
}
