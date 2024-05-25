package woowacourse.shopping.data.db.recent

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        RecentProductEntity::class,
    ],
    version = 1,
)
abstract class RecentProductDatabase : RoomDatabase() {
    abstract fun recentProductDao(): RecentProductDao

    companion object {
        private var instance: RecentProductDatabase? = null

        fun getInstance(context: Context): RecentProductDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    RecentProductDatabase::class.java,
                    "recent_product_database",
                ).build().also { instance = it }
            }
        }
    }
}
