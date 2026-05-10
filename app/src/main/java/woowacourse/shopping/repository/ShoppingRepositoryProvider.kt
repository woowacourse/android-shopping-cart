package woowacourse.shopping.repository

import android.content.Context
import woowacourse.shopping.local.ShoppingDatabase
import woowacourse.shopping.repository.inmemory.InMemoryRecentProductRepository
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.repository.room.RoomCartRepository

object ShoppingRepositoryProvider {
    val productRepository: ProductRepository = InMemoryProductRepository

    lateinit var cartRepository: CartRepository
        private set

    lateinit var recentProductRepository: RecentProductRepository
        private set

    fun initialize(context: Context) {
        if (::cartRepository.isInitialized && ::recentProductRepository.isInitialized) return

        val database = ShoppingDatabase.getInstance(context)
        cartRepository = RoomCartRepository(database.cartItemDao())
        recentProductRepository = InMemoryRecentProductRepository
    }
}
