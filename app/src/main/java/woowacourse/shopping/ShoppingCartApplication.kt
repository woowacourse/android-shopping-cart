package woowacourse.shopping

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.room.Room
import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.data.ShoppingCartDatabase
import woowacourse.shopping.data.repository.product.ProductsRepository
import woowacourse.shopping.data.repository.product.RoomProductsRepositoryImpl
import woowacourse.shopping.data.repository.recent.RecentProductsRepository
import woowacourse.shopping.data.repository.recent.RecentProductsRepositoryImpl
import woowacourse.shopping.data.repository.shoppingcart.RoomShoppingCartRepositoryImpl
import woowacourse.shopping.data.repository.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.mapper.toEntity
import kotlin.concurrent.thread

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

    val recentProductsRepository: RecentProductsRepository by lazy {
        RecentProductsRepositoryImpl(
            db.recentProductDao(),
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
            setData()
            prefs.edit { putBoolean("first_run", false) }
        }
    }

    private fun setData() {
        thread {
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
    }

    companion object {
        private const val DATABASE_NAME = "shopping-cart-database"
    }
}
