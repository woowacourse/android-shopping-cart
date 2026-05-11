package woowacourse.shopping.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.dao.ShoppingDao
import woowacourse.shopping.data.entity.CartEntity
import woowacourse.shopping.data.entity.CatalogEntity
import woowacourse.shopping.data.entity.RecentProductEntity

@Database(
    entities = [CatalogEntity::class, CartEntity::class, RecentProductEntity::class],
    version = 1
)
abstract class ShoppingDatabase: RoomDatabase() {
    abstract fun shoppingDao(): ShoppingDao
}
