package woowacourse.shopping.data.shoppingcart

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartItemEntity::class], version = 1)
abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao

    companion object {
        private const val CART_ITEM_DATABASE_NAME = "shopping_cart_database"

        @Volatile
        private var database: ShoppingCartDatabase? = null

        fun database(context: Context): ShoppingCartDatabase {
            return database ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        ShoppingCartDatabase::class.java,
                        CART_ITEM_DATABASE_NAME,
                    ).build()
                database = instance
                instance
            }
        }
    }
}
