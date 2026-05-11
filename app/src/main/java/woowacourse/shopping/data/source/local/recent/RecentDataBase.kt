package woowacourse.shopping.data.source.local.recent

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.source.local.cart.CartItemEntity

@Database(entities = [CartItemEntity::class], version = 1)
abstract class RecentDataBase : RoomDatabase() {
    abstract fun recentDao(): RecentProductDao
}
