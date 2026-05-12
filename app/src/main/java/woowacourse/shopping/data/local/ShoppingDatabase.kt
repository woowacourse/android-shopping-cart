package woowacourse.shopping.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.local.cart.CartDao
import woowacourse.shopping.data.local.cart.CartItemEntity

@Database(entities = [CartItemEntity::class], version = 1, exportSchema = false)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}
