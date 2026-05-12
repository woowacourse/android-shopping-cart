package woowacourse.shopping.di

import android.content.Context
import androidx.room.Room
import woowacourse.shopping.data.datasource.cart.CartDataSource
import woowacourse.shopping.data.datasource.cart.RoomCartDataSource
import woowacourse.shopping.data.datasource.product.ProductDataSource
import woowacourse.shopping.data.datasource.product.RemoteProductDataSource
import woowacourse.shopping.data.datasource.recent.RecentProductDataSource
import woowacourse.shopping.data.datasource.recent.RoomRecentProductDataSource
import woowacourse.shopping.data.local.ShoppingDatabase
import woowacourse.shopping.data.remote.HttpClientProvider
import woowacourse.shopping.data.mock.MockWebServerProvider
import woowacourse.shopping.data.repository.cart.LocalCartRepository
import woowacourse.shopping.data.repository.product.RemoteProductRepository
import woowacourse.shopping.data.repository.recent.LocalRecentProductRepository
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

class AppContainer(
    context:Context
) {
    private val database: ShoppingDatabase  =
        Room.databaseBuilder(
            context.applicationContext,
            ShoppingDatabase::class.java,
            "shopping.db",
        ).build()

    private val cartDataSource: CartDataSource = RoomCartDataSource(database.cartItemDao())
    private val productDataSource: ProductDataSource = RemoteProductDataSource(
        client = HttpClientProvider.okHttpClient,
        baseUrlProvider = { MockWebServerProvider.baseUrl}
    )
    private val recentProductDataSource: RecentProductDataSource = RoomRecentProductDataSource(database.recentProductDao())

    val cartRepository : CartRepository = LocalCartRepository(cartDataSource)
    val productRepository : ProductRepository = RemoteProductRepository(productDataSource)
    val recentProductRepository : RecentProductRepository = LocalRecentProductRepository(recentProductDataSource)
}
