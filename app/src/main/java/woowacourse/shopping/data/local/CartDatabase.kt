package woowacourse.shopping.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.remote.ProductDao
import woowacourse.shopping.data.local.dao.CartItemDao
import woowacourse.shopping.data.local.dao.HistoryDao
import woowacourse.shopping.data.local.entity.CartItemEntity
import woowacourse.shopping.data.local.entity.HistoryEntity
import woowacourse.shopping.data.local.entity.ProductEntity

@Database(entities = [ProductEntity::class, CartItemEntity::class, HistoryEntity::class], version = 4, exportSchema = false)
abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract fun cartDao(): ProductDao

    abstract fun cartItemDao(): CartItemDao

    abstract fun historyDao(): HistoryDao
}
