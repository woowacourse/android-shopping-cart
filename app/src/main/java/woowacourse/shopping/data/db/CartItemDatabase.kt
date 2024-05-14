package woowacourse.shopping.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.shopping.data.model.CartItemEntity

@Database(
    entities = [
        CartItemEntity::class,
    ],
    version = 1,
)
@TypeConverters(CartItemConverters::class)
abstract class CartItemDatabase : RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao

    companion object {
        private var instance: CartItemDatabase? = null
        const val CART_ITEMS_DB_NAME = "cartItems"

        @Synchronized
        fun getInstance(context: Context): CartItemDatabase {
            return instance
                ?: synchronized(CartItemDatabase::class) {
                    Room.databaseBuilder(
                        context.applicationContext,
                        CartItemDatabase::class.java,
                        CART_ITEMS_DB_NAME,
                    ).build()
                }
        }
    }
}
