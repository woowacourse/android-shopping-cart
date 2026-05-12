package woowacourse.shopping.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.local.recentproduct.RecentProductDao
import woowacourse.shopping.data.local.recentproduct.RecentProductEntity

@Database(entities = [RecentProductEntity::class], version = 1, exportSchema = false)
abstract class RecentProductDatabase : RoomDatabase() {
    abstract fun recentProductDao(): RecentProductDao
}
