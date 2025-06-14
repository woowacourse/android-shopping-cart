package woowacourse.shopping.data.source.products.recentlyviewed

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecentlyViewedEntity::class], version = 1)
abstract class RecentlyViewedDatabase : RoomDatabase() {
    abstract fun recentlyViewedDao(): RecentlyViewedDao

    companion object {
        private const val DB_NAME = "recently_viewed"
        private var instance: RecentlyViewedDatabase? = null

        @Synchronized
        fun initialize(context: Context): RecentlyViewedDatabase =
            instance ?: Room
                .databaseBuilder(
                    context,
                    RecentlyViewedDatabase::class.java,
                    DB_NAME,
                ).build()
                .also { instance = it }
    }
}
