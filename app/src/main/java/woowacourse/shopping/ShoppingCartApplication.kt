package woowacourse.shopping

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
        )
            .addCallback(InitialDataCallback())
            .build()
    }

    private inner class InitialDataCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            thread {
                DummyProducts.products.forEach {
                    this@ShoppingCartApplication.db.productDao().insert(
                        it.toEntity(),
                    )
                }

                DummyShoppingCart.items.forEach {
                    this@ShoppingCartApplication.db.shoppingCartDao().insert(
                        it.toEntity(),
                    )
                }
            }.join()
        }
    }

    companion object {
        private const val DATABASE_NAME = "shopping-cart-database"
    }
}
