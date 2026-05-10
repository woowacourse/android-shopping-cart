package woowacourse.shopping.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.local.cart.CartItemDao
import woowacourse.shopping.data.local.cart.CartItemEntity
import woowacourse.shopping.data.local.recent.RecentProductDao
import woowacourse.shopping.data.local.recent.RecentProductEntity

@Database(
    entities = [RecentProductEntity::class, CartItemEntity::class],
    version = 2,
    exportSchema = false,
)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao

    abstract fun recentProductDao(): RecentProductDao
}
