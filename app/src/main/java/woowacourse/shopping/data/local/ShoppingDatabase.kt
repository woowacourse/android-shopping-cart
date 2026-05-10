package woowacourse.shopping.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartEntity::class, RecentProductEntity::class], version = 2)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    abstract fun recentProductDao(): RecentProductDao

    companion object {
        @Volatile
        private var instance: ShoppingDatabase? = null

        fun getDatabase(context: Context): ShoppingDatabase =
            instance ?: synchronized(this) {
                val newInstance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        ShoppingDatabase::class.java,
                        "shopping_database",
                    ).build()

                instance = newInstance
                newInstance
            }
    }
}
