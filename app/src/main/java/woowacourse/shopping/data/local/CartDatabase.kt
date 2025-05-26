package woowacourse.shopping.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductEntity::class, CartItemEntity::class, HistoryEntity::class], version = 4, exportSchema = false)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    abstract fun cartItemDao(): CartItemDao

    abstract fun historyDao(): HistoryDao
}
