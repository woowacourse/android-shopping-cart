package woowacourse.shopping.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.shopping.data.local.converter.PriceConverter
import woowacourse.shopping.data.local.dao.CartDao
import woowacourse.shopping.data.local.dao.ProductDao
import woowacourse.shopping.data.local.dao.RecentlyViewedProductsDao
import woowacourse.shopping.data.local.entity.CartItemEntity
import woowacourse.shopping.data.local.entity.ProductEntity
import woowacourse.shopping.data.local.entity.RecentViewedProductsEntity

@Database(
    entities = [ProductEntity::class, CartItemEntity::class, RecentViewedProductsEntity::class],
    version = 2,
)
@TypeConverters(PriceConverter::class)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    abstract fun cartDao(): CartDao

    abstract fun recentlyViewedProductsDao(): RecentlyViewedProductsDao
}
