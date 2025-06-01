package woowacourse.shopping.data.cart

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartItem::class], version = 1)
abstract class CartItemDatabase : RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao

    companion object {
        @Volatile
        private var instance: CartItemDatabase? = null

        fun getInstance(context: Context): CartItemDatabase =
            instance ?: synchronized(this) {
                val newInstance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            CartItemDatabase::class.java,
                            "cartItemDatabase",
                        ).build()
                instance = newInstance
                newInstance
            }
    }
}
