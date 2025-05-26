package woowacourse.shopping

import android.app.Application
import androidx.room.Room
import woowacourse.shopping.data.product.local.database.ProductDatabase
import woowacourse.shopping.data.product.repository.DefaultProductsRepository
import woowacourse.shopping.data.shoppingCart.local.database.ShoppingCartDatabase
import woowacourse.shopping.data.shoppingCart.repository.DefaultShoppingCartRepository

class ShoppingApplication : Application() {
    private val shoppingCartDatabase: ShoppingCartDatabase by lazy {
        Room
            .databaseBuilder(
                applicationContext,
                ShoppingCartDatabase::class.java,
                "shoppingCart",
            ).fallbackToDestructiveMigration()
            .build()
    }

    private val productDatabase: ProductDatabase by lazy {
        Room
            .databaseBuilder(
                applicationContext,
                ProductDatabase::class.java,
                "recent_watching",
            ).fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        DefaultShoppingCartRepository.initialize(shoppingCartDatabase.shoppingCartDao())
        DefaultProductsRepository.initialize(productDatabase.recentWatchingDao())
    }
}
