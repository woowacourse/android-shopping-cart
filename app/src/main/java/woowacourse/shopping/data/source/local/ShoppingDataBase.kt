package woowacourse.shopping.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CartItemEntity::class], version = 1)
abstract class ShoppingDataBase : RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao
}
