package woowacourse.shopping.data.recent

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecentProductEntity::class], version = 1)
abstract class RecentProductDatabase : RoomDatabase() {
    abstract fun dao(): RecentProductDao

    companion object {
        private var instance: RecentProductDatabase? = null

        @Synchronized
        fun getInstance(context: Context): RecentProductDatabase {
            return instance ?: Room.databaseBuilder(
                context.applicationContext,
                RecentProductDatabase::class.java,
                "recentProduct.db",
            ).build()
        }
    }
}
