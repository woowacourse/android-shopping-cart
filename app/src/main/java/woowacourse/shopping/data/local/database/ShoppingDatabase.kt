package woowacourse.shopping.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.local.dao.CartDao
import woowacourse.shopping.data.local.dao.RecentlyViewedProductDao
import woowacourse.shopping.data.local.entity.CartItemEntity
import woowacourse.shopping.data.local.entity.RecentlyViewedProductEntity

@Database(
    entities = [
        CartItemEntity::class,
        RecentlyViewedProductEntity::class,
    ],
    version = 3,
    exportSchema = false,
)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    abstract fun recentlyViewedProductDao(): RecentlyViewedProductDao
}
