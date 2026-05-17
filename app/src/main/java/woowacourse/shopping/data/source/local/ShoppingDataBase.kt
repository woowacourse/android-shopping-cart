package woowacourse.shopping.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.source.local.cart.CartItemDao
import woowacourse.shopping.data.source.local.cart.CartItemEntity
import woowacourse.shopping.data.source.local.recent.RecentProductDao
import woowacourse.shopping.data.source.local.recent.RecentProductEntity

@Database(entities = [CartItemEntity::class, RecentProductEntity::class], version = 1)
abstract class ShoppingDataBase : RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao

    abstract fun recentItemDao(): RecentProductDao
}
