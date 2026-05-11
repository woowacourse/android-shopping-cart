package woowacourse.shopping

import android.content.Context
import woowacourse.shopping.data.localdb.ShoppingDB
import woowacourse.shopping.data.network.HttpProductServer
import woowacourse.shopping.data.network.NetworkManager
import woowacourse.shopping.data.network.NetworkObserver
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.HttpProductRepository
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentItemRepository

class AppContainer(
    context: Context,
) {
    private val database = ShoppingDB.getInstance(context)

    val productRepository: ProductRepository by lazy {
        HttpProductRepository(HttpProductServer.baseUrl)
    }

    val cartRepository: CartRepository by lazy {
        CartRepository(database.cartItemDao(), productRepository)
    }

    val recentItemRepository: RecentItemRepository by lazy {
        RecentItemRepository(database.recentItemDao(), productRepository)
    }

    val networkObserver: NetworkObserver by lazy {
        NetworkManager(context)
    }
}
