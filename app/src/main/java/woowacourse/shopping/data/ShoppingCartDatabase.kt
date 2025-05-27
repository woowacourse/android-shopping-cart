package woowacourse.shopping.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.shopping.data.converter.Converter
import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.dao.RecentProductDao
import woowacourse.shopping.data.dao.ShoppingCartItemDao
import woowacourse.shopping.data.entity.ProductEntity
import woowacourse.shopping.data.entity.RecentProductEntity
import woowacourse.shopping.data.entity.ShoppingCartItemEntity

@Database(
    entities = [
        ShoppingCartItemEntity::class,
        ProductEntity::class,
        RecentProductEntity::class,
    ],
    version = 1,
)
@TypeConverters(Converter::class)
abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract fun shoppingCartDao(): ShoppingCartItemDao

    abstract fun productDao(): ProductDao

    abstract fun recentProductDao(): RecentProductDao
}
