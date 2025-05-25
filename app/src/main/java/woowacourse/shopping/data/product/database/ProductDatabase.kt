package woowacourse.shopping.data.product.database

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.product.dao.RecentWatchingDao
import woowacourse.shopping.data.product.entity.ProductEntity
import woowacourse.shopping.data.product.entity.RecentWatchingEntity

@Database(
    entities = [ProductEntity::class, RecentWatchingEntity::class],
    version = 2,
)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun recentWatchingDao(): RecentWatchingDao
}
