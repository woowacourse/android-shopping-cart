package woowacourse.shopping

import android.app.Application
import androidx.room.Room
import woowacourse.shopping.data.shoppingCart.database.ShoppingCartDatabase
import woowacourse.shopping.data.shoppingCart.repository.DefaultShoppingCartRepository

class ShoppingApplication : Application() {
    private val shoppingCartDatabase: ShoppingCartDatabase by lazy {
        Room
            .databaseBuilder(
                applicationContext,
                ShoppingCartDatabase::class.java,
                "shoppingCart",
            ).build()
    }

    override fun onCreate() {
        super.onCreate()
        DefaultShoppingCartRepository.initialize(shoppingCartDatabase.shoppingCartDao())
    }
}
