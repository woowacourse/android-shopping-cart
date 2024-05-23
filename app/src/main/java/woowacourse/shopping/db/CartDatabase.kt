package woowacourse.shopping.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cart::class], version = 1)
abstract class CartDatabase: RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var instance: CartDatabase? = null
        private const val CART_DATABASE = "cartDatabase"

        fun getInstance(context: Context): CartDatabase {
            var instance = instance

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    CartDatabase::class.java,
                    CART_DATABASE,
                ).build()
            }

            return instance
        }
    }
}