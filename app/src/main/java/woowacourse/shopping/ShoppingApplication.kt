package woowacourse.shopping

import android.app.Application
import androidx.room.Room
import woowacourse.shopping.data.common.database.AppDatabase
import woowacourse.shopping.data.product.repository.DefaultProductsRepository
import woowacourse.shopping.data.shoppingCart.repository.DefaultShoppingCartRepository

class ShoppingApplication : Application() {
    private val appDatabase: AppDatabase by lazy {
        Room
            .databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "database",
            ).fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        DefaultShoppingCartRepository.initialize(appDatabase.shoppingCartDao())
        DefaultProductsRepository.initialize(appDatabase.recentWatchingDao())
    }
}
