package woowacourse.shopping.data.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.product.local.dao.RecentWatchingDao
import woowacourse.shopping.data.product.local.entity.ProductEntity
import woowacourse.shopping.data.product.local.entity.RecentWatchingEntity
import woowacourse.shopping.data.shoppingCart.local.dao.ShoppingCartDao
import woowacourse.shopping.data.shoppingCart.local.entity.ShoppingCartProductEntity

@Database(
    entities = [
        ShoppingCartProductEntity::class,
        ProductEntity::class,
        RecentWatchingEntity::class,
    ],
    version = 4,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recentWatchingDao(): RecentWatchingDao

    abstract fun shoppingCartDao(): ShoppingCartDao
}
