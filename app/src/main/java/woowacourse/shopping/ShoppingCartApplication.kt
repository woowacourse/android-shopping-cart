package woowacourse.shopping

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.data.ShoppingCartDatabase
import woowacourse.shopping.data.repository.ProductsRepository
import woowacourse.shopping.data.repository.RoomProductsRepositoryImpl
import woowacourse.shopping.data.repository.RoomShoppingCartRepositoryImpl
import woowacourse.shopping.data.repository.ShoppingCartRepository
import woowacourse.shopping.mapper.toEntity

class ShoppingCartApplication : Application() {
    val productsRepository: ProductsRepository by lazy {
        RoomProductsRepositoryImpl(
            db.productDao(),
        )
    }
    val shoppingCartRepository: ShoppingCartRepository by lazy {
        RoomShoppingCartRepositoryImpl(
            db.shoppingCartDao(),
        )
    }

    val db: ShoppingCartDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            ShoppingCartDatabase::class.java,
            DATABASE_NAME,
        ).build()
    }

    override fun onCreate() {
        super.onCreate()

        val prefs: SharedPreferences = getSharedPreferences("first_run", MODE_PRIVATE)
        val isFirstRun = prefs.getBoolean("first_run", true)

        if (isFirstRun) {
            CoroutineScope(Dispatchers.IO).launch {
                DummyProducts.products.forEach {
                    db.productDao().insert(
                        it.toEntity(),
                    )
                }

                DummyShoppingCart.items.forEach {
                    db.shoppingCartDao().insert(
                        it.toEntity(),
                    )
                }
            }
            prefs.edit { putBoolean("first_run", false) }
        }
    }

    companion object {
        private const val DATABASE_NAME = "shopping-cart-database"
    }
}
