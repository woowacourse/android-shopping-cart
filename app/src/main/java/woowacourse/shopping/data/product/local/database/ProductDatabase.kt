package woowacourse.shopping.data.product.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.product.local.dao.RecentWatchingDao
import woowacourse.shopping.data.product.local.entity.ProductEntity
import woowacourse.shopping.data.product.local.entity.RecentWatchingEntity

@Database(
    entities = [ProductEntity::class, RecentWatchingEntity::class],
    version = 2,
)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun recentWatchingDao(): RecentWatchingDao
}
