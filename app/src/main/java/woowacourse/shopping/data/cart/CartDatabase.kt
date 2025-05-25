package woowacourse.shopping.data.cart

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.history.HistoryDao
import woowacourse.shopping.data.history.HistoryEntity
import woowacourse.shopping.data.product.ProductEntity

@Database(entities = [ProductEntity::class, CartItemEntity::class, HistoryEntity::class], version = 4)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    abstract fun cartItemDao(): CartItemDao

    abstract fun historyDao(): HistoryDao
}
