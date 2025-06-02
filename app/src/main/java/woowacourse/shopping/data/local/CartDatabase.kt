package woowacourse.shopping.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.local.dao.CartItemDao
import woowacourse.shopping.data.local.dao.HistoryDao
import woowacourse.shopping.data.local.entity.CartItemEntity
import woowacourse.shopping.data.local.entity.HistoryEntity

@Database(entities = [CartItemEntity::class, HistoryEntity::class], version = 5, exportSchema = false)
abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao

    abstract fun historyDao(): HistoryDao
}
