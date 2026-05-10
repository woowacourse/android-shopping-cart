package woowacourse.shopping.local

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.local.dao.CartDao
import woowacourse.shopping.local.entity.CartEntity

@Database(entities = [CartEntity::class], version = 1)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}
