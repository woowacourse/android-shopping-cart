package woowacourse.shopping.local

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.local.dao.CartDao
import woowacourse.shopping.local.dao.RecentProductDao
import woowacourse.shopping.local.entity.CartEntity
import woowacourse.shopping.local.entity.RecentProductEntity

@Database(entities = [CartEntity::class, RecentProductEntity::class], version = 3)
abstract class Database : RoomDatabase() {
    abstract fun cartDao(): CartDao

    abstract fun recentProductDao(): RecentProductDao
}
