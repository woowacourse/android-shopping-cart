package woowacourse.shopping.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.shopping.data.db.dao.HistoryDao
import woowacourse.shopping.data.db.dao.ProductDao
import woowacourse.shopping.data.db.model.HistoryEntity
import woowacourse.shopping.data.db.model.ProductEntity

@Database(
    version = 1,
    entities = [
        ProductEntity::class,
        HistoryEntity::class,
    ],
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    abstract fun historyDao(): HistoryDao
}
