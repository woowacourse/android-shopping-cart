package woowacourse.shopping.di

import android.content.Context
import androidx.room.Room
import woowacourse.shopping.data.local.ShoppingDatabase
import woowacourse.shopping.data.remote.HttpClientProvider
import woowacourse.shopping.data.mock.MockWebServerProvider
import woowacourse.shopping.data.remote.api.ProductApi
import woowacourse.shopping.data.remote.api.OkHttpProductApi
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
            "shooping.db",
        ).fallbackToDestructiveMigration(dropAllTables = true)
            .build()

    val productApi: ProductApi =
        OkHttpProductApi(
            client = HttpClientProvider.okHttpClient,
            baseUrlProvider = { MockWebServerProvider.baseUrl },
        )

    val cartRepository :CartRepository = LocalCartRepository(database.cartItemDao())
    val productRepository : ProductRepository = RemoteProductRepository(productApi)
    val recentProductRepository : RecentProductRepository = LocalRecentProductRepository(database.recentProductDao())
}
