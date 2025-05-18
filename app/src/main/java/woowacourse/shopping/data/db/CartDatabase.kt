package woowacourse.shopping.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartEntity::class], version = 1)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        private const val DB_NAME = "cart"

        @Volatile
        private var instance: CartDatabase? = null

        fun getInstance(context: Context): CartDatabase =
            instance ?: synchronized(this) {
                instance ?: createDatabase(context).also { instance = it }
            }

        private fun createDatabase(context: Context): CartDatabase =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    CartDatabase::class.java,
                    DB_NAME,
                ).build()
    }
}
