package woowacourse.shopping.di

import android.content.Context
import androidx.room.Room
import woowacourse.shopping.local.Database
import woowacourse.shopping.network.NetworkClient
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentProductRepository
import woowacourse.shopping.repository.network.NetworkProductRepository
import woowacourse.shopping.repository.room.RoomCartRepository
import woowacourse.shopping.repository.room.RoomRecentProductRepository

object AppContainer {
    private lateinit var database: Database

    val networkClient = NetworkClient()
    val productRepository: ProductRepository =
        NetworkProductRepository(networkClient = networkClient)
    val cartRepository: CartRepository by lazy {
        RoomCartRepository(
            cartDao = database.cartDao(),
            productRepository = productRepository,
        )
    }
    val recentProductRepository: RecentProductRepository by lazy {
        RoomRecentProductRepository(
            recentProductDao = database.recentProductDao(),
            productRepository = productRepository,
        )
    }

    fun init(context: Context) {
        database =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    Database::class.java,
                    "shopping-db",
                ).fallbackToDestructiveMigration(false)
                .build()
    }
}
