package woowacourse.shopping.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.repository.dao.ProductDao
import woowacourse.shopping.repository.dao.ShoppingCartItemDao
import woowacourse.shopping.repository.entity.ProductEntity
import woowacourse.shopping.repository.entity.ShoppingCartItemEntity

@Database(entities = [ProductEntity::class, ShoppingCartItemEntity::class], version = 1)
abstract class AndroidShoppingDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    abstract fun shoppingCartItemDao(): ShoppingCartItemDao
}
