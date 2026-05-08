package woowacourse.shopping.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.source.local.cart.CartDao
import woowacourse.shopping.data.source.local.cart.CartEntity
import woowacourse.shopping.data.source.local.recent.RecentProductDao
import woowacourse.shopping.data.source.local.recent.RecentProductEntity

@Database(
    entities = [CartEntity::class, RecentProductEntity::class],
    version = 1,
)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    abstract fun recentProductDao(): RecentProductDao
}
