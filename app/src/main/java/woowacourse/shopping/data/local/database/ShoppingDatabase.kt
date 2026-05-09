package woowacourse.shopping.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.local.dao.CartDao
import woowacourse.shopping.data.local.entity.CartItemEntity

@Database(entities = [CartItemEntity::class], version = 1)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}
