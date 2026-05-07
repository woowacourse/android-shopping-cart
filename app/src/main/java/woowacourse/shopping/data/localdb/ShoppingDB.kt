package woowacourse.shopping.data.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.localdb.dao.CartItemDao
import woowacourse.shopping.data.localdb.dao.RecentItemDao
import woowacourse.shopping.data.localdb.entity.CartItemEntity
import woowacourse.shopping.data.localdb.entity.RecentItemEntity

@Database(
    entities = [CartItemEntity::class, RecentItemEntity::class],
    version = 1,
)
abstract class ShoppingDB : RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao
    abstract fun recentItemDao(): RecentItemDao
}