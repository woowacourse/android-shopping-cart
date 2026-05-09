package woowacourse.shopping.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.local.recent.RecentProductDao
import woowacourse.shopping.data.local.recent.RecentProductEntity

@Database(
    entities = [RecentProductEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun recentProductDao(): RecentProductDao
}
