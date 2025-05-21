package woowacourse.shopping.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.cart.CartItem
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.products.ProductRepository
import woowacourse.shopping.data.products.RecentProduct

@Database(entities = [RecentProduct::class, CartItem::class], version = 1)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun cartRepository(): CartRepository

    abstract fun productRepository(): ProductRepository

    companion object {
        @Volatile
        private var INSTANCE: ShoppingDatabase? = null

        fun getDatabase(context: Context): ShoppingDatabase =
            INSTANCE ?: synchronized(this) {
                val instance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            ShoppingDatabase::class.java,
                            "shopping_cart_db",
                        ).build()
                INSTANCE = instance
                instance
            }
    }
}
