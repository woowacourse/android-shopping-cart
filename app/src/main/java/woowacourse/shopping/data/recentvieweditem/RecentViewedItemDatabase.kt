package woowacourse.shopping.data.recentvieweditem

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [RecentViewedItemEntity::class],
    version = 1,
)
@TypeConverters(RecentViewedItemConverters::class)
abstract class RecentViewedItemDatabase : RoomDatabase() {
    abstract fun recentViewedItemDao(): RecentViewedItemDao

    companion object {
        private var instance: RecentViewedItemDatabase? = null
        const val RECENT_VIEWED_ITEM_DB_NAME = "recentViewedItems"

        @Synchronized
        fun getInstance(context: Context): RecentViewedItemDatabase {
            return instance ?: synchronized(RecentViewedItemDatabase::class) {
                Room.databaseBuilder(
                    context.applicationContext,
                    RecentViewedItemDatabase::class.java,
                    RECENT_VIEWED_ITEM_DB_NAME,
                ).build()
            }
        }
    }
}
