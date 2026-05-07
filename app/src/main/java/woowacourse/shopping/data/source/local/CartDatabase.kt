package woowacourse.shopping.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CartEntity::class],
    version = 1,
)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}
