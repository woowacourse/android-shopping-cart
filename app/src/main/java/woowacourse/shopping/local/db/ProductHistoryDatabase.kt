package woowacourse.shopping.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.shopping.local.dao.ProductHistoryDao
import woowacourse.shopping.local.model.ProductHistoryEntity

@Database(entities = [ProductHistoryEntity::class], version = 1)
@TypeConverters(ProductHistoryTypeConverters::class)
abstract class ProductHistoryDatabase : RoomDatabase() {
    abstract fun dao(): ProductHistoryDao

    companion object {
        @Volatile
        private var instance: ProductHistoryDatabase? = null
        private const val DATABASE_NAME = "product-history-database"

        fun getDatabase(context: Context): ProductHistoryDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    ProductHistoryDatabase::class.java,
                    DATABASE_NAME,
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}
