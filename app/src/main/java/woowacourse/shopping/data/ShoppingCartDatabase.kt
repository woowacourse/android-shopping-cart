package woowacourse.shopping.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.shopping.data.product.LocalDateConverters
import woowacourse.shopping.data.product.dao.ProductDao
import woowacourse.shopping.data.product.dao.RecentViewedProductDao
import woowacourse.shopping.data.product.entity.CartItemEntity
import woowacourse.shopping.data.product.entity.ProductEntity
import woowacourse.shopping.data.product.entity.RecentViewedProductEntity
import woowacourse.shopping.data.shoppingCart.dao.ShoppingCartDao

@Database(
    entities = [ProductEntity::class, RecentViewedProductEntity::class, CartItemEntity::class],
    version = 1,
)
@TypeConverters(LocalDateConverters::class)
abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract val productDao: ProductDao

    abstract val recentViewedProductDao: RecentViewedProductDao

    abstract val shoppingCartDao: ShoppingCartDao
}
