package woowacourse.shopping.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartProduct::class], version = 1)
abstract class CartProductDatabase : RoomDatabase() {
    abstract fun cartProductDao(): CartProductDao

    companion object {
        @Volatile
        private var instance: CartProductDatabase? = null

        fun getInstance(context: Context): CartProductDatabase =
            instance ?: synchronized(this) {
                val newInstance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            CartProductDatabase::class.java,
                            "cartProductDatabase",
                        ).build()
                instance = newInstance
                newInstance
            }
    }
}
