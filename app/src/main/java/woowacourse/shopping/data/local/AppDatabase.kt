package woowacourse.shopping.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CartEntity::class, RecentProductEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun recentProductDao(): RecentProductDao
}
