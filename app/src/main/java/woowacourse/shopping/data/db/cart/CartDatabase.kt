package woowacourse.shopping.data.db.cart

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        CartItemEntity::class,
    ],
    version = 1,
)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        private var instance: CartDatabase? = null

        fun getInstance(context: Context): CartDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    CartDatabase::class.java,
                    "cart_database",
                ).build().also { instance = it }
            }
        }
    }
}
