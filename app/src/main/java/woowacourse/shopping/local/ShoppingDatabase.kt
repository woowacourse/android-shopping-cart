package woowacourse.shopping.local

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.local.dao.CartDao
import woowacourse.shopping.local.dao.RecentItemDao
import woowacourse.shopping.local.entity.CartEntity
import woowacourse.shopping.local.entity.RecentItemEntity

@Database(entities = [CartEntity::class, RecentItemEntity::class], version = 2)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun recentItemDao(): RecentItemDao
}
