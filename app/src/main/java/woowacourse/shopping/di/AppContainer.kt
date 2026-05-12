package woowacourse.shopping.di

import android.content.Context
import androidx.room.Room
import woowacourse.shopping.data.local.ShoppingDatabase
import woowacourse.shopping.data.remote.HttpClientProvider
import woowacourse.shopping.data.remote.MockWebServerProvider
import woowacourse.shopping.data.remote.api.ProductService
import woowacourse.shopping.data.remote.api.ProductServiceImpl
import woowacourse.shopping.data.repository.cart.RoomCartRepository
import woowacourse.shopping.data.repository.product.RemoteProductRepository
import woowacourse.shopping.data.repository.recent.RoomRecentProductRepository
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
            "shooping.db",
        ).fallbackToDestructiveMigration(dropAllTables = true)
            .build()

    val productService: ProductService =
        ProductServiceImpl(
            client = HttpClientProvider.okHttpClient,
            baseUrlProvider = { MockWebServerProvider.baseUrl },
        )

    val cartRepository :CartRepository = RoomCartRepository(database.cartItemDao())
    val productRepository : ProductRepository = RemoteProductRepository(productService)
    val recentProductRepository : RecentProductRepository = RoomRecentProductRepository(database.recentProductDao())
}
