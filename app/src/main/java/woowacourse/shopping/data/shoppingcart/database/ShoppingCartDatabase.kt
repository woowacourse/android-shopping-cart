package woowacourse.shopping.data.shoppingcart.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShoppingCartEntity::class], version = 1)
abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract fun shoppingCartDao(): ShoppingCartDao

    companion object {
        @Volatile
        private var INSTANCE: ShoppingCartDatabase? = null

        fun getDatabase(context: Context): ShoppingCartDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    ShoppingCartDatabase::class.java,
                    "shopping_cart"
                ).build().also { INSTANCE = it }
            }
        }
    }
}