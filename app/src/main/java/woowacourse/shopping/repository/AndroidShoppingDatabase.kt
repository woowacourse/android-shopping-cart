package woowacourse.shopping.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.repository.dao.ShoppingCartItemDao
import woowacourse.shopping.repository.entity.ShoppingCartItemEntity

@Database(entities = [ShoppingCartItemEntity::class], version = 1)
abstract class AndroidShoppingDatabase : RoomDatabase() {
    abstract fun shoppingCartItemDao(): ShoppingCartItemDao
}
