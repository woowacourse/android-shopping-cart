package woowacourse.shopping.view.data

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.product.entity.CartItemEntity

@Database(entities = [CartItemEntity::class], version = 1)
abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract fun dao(): ShoppingCartDao
}
