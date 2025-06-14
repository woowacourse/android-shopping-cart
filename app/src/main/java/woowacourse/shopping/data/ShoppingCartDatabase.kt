package woowacourse.shopping.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        private const val DATABASE_NAME = "shopping_cart_database"

        @Volatile
        private var instance: ShoppingCartDatabase? = null

        fun getDataBase(context: Context): ShoppingCartDatabase =
            instance ?: synchronized(this) {
                val newInstance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            ShoppingCartDatabase::class.java,
                            DATABASE_NAME,
                        ).build()
                instance = newInstance
                newInstance
            }
    }
}
