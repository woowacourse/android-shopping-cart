package woowacourse.shopping.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.local.dao.CartDao
import woowacourse.shopping.data.local.dao.RecentProductDao
import woowacourse.shopping.data.local.entity.CartEntity
import woowacourse.shopping.data.local.entity.RecentProductEntity

@Database(entities = [CartEntity::class, RecentProductEntity::class], version = 3)
abstract class Database : RoomDatabase() {
    abstract fun cartDao(): CartDao

    abstract fun recentProductDao(): RecentProductDao
}
