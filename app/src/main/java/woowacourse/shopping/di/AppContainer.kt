package woowacourse.shopping.di

import android.content.Context
import androidx.room.Room
import woowacourse.shopping.local.ShoppingDatabase
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.inmemory.InMemoryProductRepository
import woowacourse.shopping.repository.room.RoomCartRepository

object AppContainer {
    private lateinit var database: ShoppingDatabase

    val productRepository: ProductRepository = InMemoryProductRepository()
    val cartRepository: CartRepository by lazy {
        RoomCartRepository(
            cartDao = database.cartDao(),
            productRepository = productRepository
        )
    }

    fun init(context: Context) {
        database = Room.databaseBuilder(
            context.applicationContext,
            ShoppingDatabase::class.java,
            "shopping-db"
        ).build()
    }
}
