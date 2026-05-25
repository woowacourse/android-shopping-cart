package woowacourse.shopping

import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import woowacourse.shopping.data.db.ShoppingDatabase
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.repository.cart.RoomCartRepository
import woowacourse.shopping.repository.product.InMemoryProductRepository
import woowacourse.shopping.repository.recent_product.RoomRecentProductRepository

class ShoppingApplication : Application() {
    val database by lazy {
        Room.databaseBuilder(this, ShoppingDatabase::class.java, "shopping.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    val cartRepository by lazy {
        RoomCartRepository(
            dao = database.shoppingDao(),
            externalScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        )
    }

    val recentProductRepository by lazy {
        RoomRecentProductRepository(
            dao = database.shoppingDao()
        )
    }

    val productRepository: ProductRepository by lazy {
        InMemoryProductRepository()
    }
}
