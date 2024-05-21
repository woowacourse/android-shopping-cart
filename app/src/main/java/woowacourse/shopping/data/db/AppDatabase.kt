package woowacourse.shopping.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.db.dao.ProductDao
import woowacourse.shopping.data.db.entity.ProductEntity

@Database(
    version = 1,
    entities = [
        ProductEntity::class,
    ],
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
