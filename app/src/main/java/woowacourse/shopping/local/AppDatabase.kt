package woowacourse.shopping.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.local.dao.CartDao
import woowacourse.shopping.local.dao.RecentProductDao
import woowacourse.shopping.local.entity.CartEntity
import woowacourse.shopping.local.entity.RecentProductEntity

@Database(entities = [CartEntity::class, RecentProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    abstract fun recentProductDao(): RecentProductDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            return instance ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "db",
            ).build()
        }
    }
}
