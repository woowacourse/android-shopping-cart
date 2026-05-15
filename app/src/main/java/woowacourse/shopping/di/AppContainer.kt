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
