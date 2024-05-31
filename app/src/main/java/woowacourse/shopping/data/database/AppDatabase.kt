package woowacourse.shopping.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import woowacourse.shopping.data.dao.CartDao
import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.dao.RecentlyProductDao
import woowacourse.shopping.data.dummy.DummyShoppingItems
import woowacourse.shopping.data.model.CartItemEntity
import woowacourse.shopping.data.model.ProductEntity
import woowacourse.shopping.data.model.RecentlyViewedProductEntity
import kotlin.concurrent.thread

@Database(
    entities = [
        CartItemEntity::class,
        ProductEntity::class,
        RecentlyViewedProductEntity::class,
    ],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    abstract fun productDao(): ProductDao

    abstract fun recentlyProductDao(): RecentlyProductDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "shopping_database",
                ).addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            thread {
                                getInstance(context).productDao().insertProducts(DummyShoppingItems.items)
                            }
                        }
                    },
                ).build().also { instance = it }
            }
        }
    }
}
