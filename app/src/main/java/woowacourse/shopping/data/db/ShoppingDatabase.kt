package woowacourse.shopping.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductEntity::class], version = 1)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        private const val DATABASE_NAME = "shopping-db"

        @Volatile
        private var instance: ShoppingDatabase? = null

        fun getDatabase(context: Context): ShoppingDatabase =
            instance ?: synchronized(this) {
                instance ?: createDatabase(context).also { instance = it }
            }

        private fun createDatabase(context: Context): ShoppingDatabase =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    ShoppingDatabase::class.java,
                    DATABASE_NAME,
                ).build()
    }
}
