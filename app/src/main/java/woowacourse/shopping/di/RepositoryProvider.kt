package woowacourse.shopping.di

import android.content.Context
import androidx.room.Room
import woowacourse.shopping.data.repository.LocalCartRepository
import woowacourse.shopping.data.repository.LocalRecentProductRepository
import woowacourse.shopping.data.repository.RemoteProductRepository
import woowacourse.shopping.data.source.local.ShoppingDatabase
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

object RepositoryProvider {
    private lateinit var database: ShoppingDatabase

    val productRepository: ProductRepository = RemoteProductRepository()
    val cartRepository: CartRepository by lazy {
        LocalCartRepository(
            database.cartDao(),
            productRepository,
        )
    }

    val recentProductRepository: RecentProductRepository by lazy {
        LocalRecentProductRepository(
            database.recentProductDao(),
            productRepository,
        )
    }

    fun init(context: Context) {
        database =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    ShoppingDatabase::class.java,
                    "cart-db",
                ).build()
    }
}
