package woowacourse.shopping.data.db.recently

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.model.RecentlyProductEntity

@Database(
    entities = [
        RecentlyProductEntity::class,
    ],
    version = 1,
)
abstract class RecentlyProductDatabase : RoomDatabase() {
    abstract fun recentlyProductDao(): RecentlyProductDao

    companion object {
        private var instance: RecentlyProductDatabase? = null
        const val RECENTLY_ITEM_DB_NAME = "recentlyProducts"

        @Synchronized
        fun getInstance(context: Context): RecentlyProductDatabase {
            return instance
                ?: synchronized(RecentlyProductDatabase::class) {
                    Room.databaseBuilder(
                        context.applicationContext,
                        RecentlyProductDatabase::class.java,
                        RECENTLY_ITEM_DB_NAME,
                    ).build()
                }
        }
    }
}
