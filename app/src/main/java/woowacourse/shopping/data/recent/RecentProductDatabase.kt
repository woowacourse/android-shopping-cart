package woowacourse.shopping.data.recent

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecentProductEntity::class], version = 1)
abstract class RecentProductDatabase : RoomDatabase() {
    abstract fun recentProductDao(): RecentProductDao

    companion object {
        private const val RECENT_PRODUCT_DATABASE_NAME = "recent_product_database"

        @Volatile
        private var database: RecentProductDatabase? = null

        fun database(context: Context): RecentProductDatabase {
            return database ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        RecentProductDatabase::class.java,
                        RECENT_PRODUCT_DATABASE_NAME,
                    ).build()
                database = instance
                instance
            }
        }
    }
}
