package woowacourse.shopping.di

import android.content.Context
import androidx.room.Room
import woowacourse.shopping.data.local.Database
import woowacourse.shopping.data.remote.NetworkClient
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentProductRepository
import woowacourse.shopping.data.repository.remote.NetworkProductRepository
import woowacourse.shopping.data.repository.room.RoomCartRepository
import woowacourse.shopping.data.repository.room.RoomRecentProductRepository

class AppContainer(context: Context) {
    private val database = Room
        .databaseBuilder(
            context.applicationContext,
            Database::class.java,
            "shopping-db",
        ).fallbackToDestructiveMigration(false)
        .build()
    private val networkClient = NetworkClient()

    val productRepository: ProductRepository =
        NetworkProductRepository(networkClient = networkClient)
    val cartRepository: CartRepository =
        RoomCartRepository(
            cartDao = database.cartDao(),
            productRepository = productRepository,
        )
    val recentProductRepository: RecentProductRepository =
        RoomRecentProductRepository(
            recentProductDao = database.recentProductDao(),
            productRepo = productRepository,
        )
}
