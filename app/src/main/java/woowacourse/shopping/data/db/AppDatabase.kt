package woowacourse.shopping.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import woowacourse.shopping.data.db.dao.OrderDao
import woowacourse.shopping.data.db.dao.ProductBrowsingHistoryDao
import woowacourse.shopping.data.db.dao.ProductDao
import woowacourse.shopping.data.db.model.OrderEntity
import woowacourse.shopping.data.db.model.ProductBrowsingHistoryEntity
import woowacourse.shopping.data.db.model.ProductEntity

@Database(
    version = 2,
    entities = [
        ProductEntity::class,
        ProductBrowsingHistoryEntity::class,
        OrderEntity::class,
    ],
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    abstract fun historyDao(): ProductBrowsingHistoryDao

    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var dbInstance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return dbInstance ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "shopping_database",
                    ).addCallback(
                        object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onOpen(db)
                            }
                        },
                    ).fallbackToDestructiveMigration().build()
                dbInstance = instance
                instance
            }
        }
    }
}
