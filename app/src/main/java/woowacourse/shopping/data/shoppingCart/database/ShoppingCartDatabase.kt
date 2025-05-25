package woowacourse.shopping.data.shoppingCart.database

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.product.entity.CartItemEntity
import woowacourse.shopping.data.shoppingCart.dao.ShoppingCartDao

@Database(entities = [CartItemEntity::class], version = 1)
abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract fun dao(): ShoppingCartDao
}
