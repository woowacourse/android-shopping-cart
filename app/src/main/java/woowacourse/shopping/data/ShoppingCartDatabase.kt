package woowacourse.shopping.data

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.dao.ShoppingCartItemDao
import woowacourse.shopping.data.entity.ProductEntity
import woowacourse.shopping.data.entity.ShoppingCartItemEntity

@Database(
    entities = [
        ShoppingCartItemEntity::class,
        ProductEntity::class,
    ],
    version = 1,
)
abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract fun shoppingCartDao(): ShoppingCartItemDao

    abstract fun productDao(): ProductDao
}
