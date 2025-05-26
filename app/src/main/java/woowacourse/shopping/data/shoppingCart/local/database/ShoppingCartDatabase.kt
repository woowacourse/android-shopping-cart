package woowacourse.shopping.data.shoppingCart.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.shoppingCart.local.dao.ShoppingCartDao
import woowacourse.shopping.data.shoppingCart.local.entity.ShoppingCartProductEntity

@Database(entities = [ShoppingCartProductEntity::class], version = 2)
abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract fun shoppingCartDao(): ShoppingCartDao
}
