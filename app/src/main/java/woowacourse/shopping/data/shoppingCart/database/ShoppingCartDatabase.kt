package woowacourse.shopping.data.shoppingCart.database

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.shoppingCart.dao.ShoppingCartDao
import woowacourse.shopping.data.shoppingCart.entity.ShoppingCartProductEntity

@Database(entities = [ShoppingCartProductEntity::class], version = 1)
abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract fun shoppingCartDao(): ShoppingCartDao
}
