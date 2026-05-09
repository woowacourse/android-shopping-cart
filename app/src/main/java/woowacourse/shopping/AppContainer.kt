package woowacourse.shopping

import android.content.Context
import androidx.room.Room
import woowacourse.shopping.data.local.db.ShoppingDatabase
import woowacourse.shopping.data.local.entity.RecentViewedProductsEntity
import woowacourse.shopping.data.local.mapper.toEntity
import woowacourse.shopping.repository.cart.CartRepository
import woowacourse.shopping.repository.cart.RoomCartRepository
import woowacourse.shopping.repository.product.ProductRepository
import woowacourse.shopping.repository.product.RoomProductRepository
import woowacourse.shopping.repository.recentviewedproduct.RecentlyViewedProductsRepository
import woowacourse.shopping.repository.recentviewedproduct.RoomRecentlyViewedProductsRepository

object AppContainer {
    private lateinit var database: ShoppingDatabase

    fun initialize(context: Context) {
        database =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    ShoppingDatabase::class.java,
                    "shopping.db",
                ).fallbackToDestructiveMigration(dropAllTables = true)
                .build()
    }

    val productRepository: ProductRepository by lazy {
        RoomProductRepository(
            productDao = database.productDao(),
        )
    }

    val cartRepository: CartRepository by lazy {
        RoomCartRepository(
            cartDao = database.cartDao(),
        )
    }

    val recentlyViewedProductsRepository: RecentlyViewedProductsRepository by lazy {
        RoomRecentlyViewedProductsRepository(
            recentlyViewedProductsDao = database.recentlyViewedProductsDao(),
            productsDao = database.productDao(),
        )
    }

    suspend fun seedProducts(packageName: String) {
        database.productDao().upsertAll(
            ProductFixture.productList(packageName).map { it.toEntity() },
        )
    }
}
