package woowacourse.shopping.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.shopping.local.dao.ShoppingCartDao
import woowacourse.shopping.local.model.CartProductEntity

@Database(entities = [CartProductEntity::class], version = 1)
@TypeConverters(ProductHistoryTypeConverters::class)
abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract fun dao(): ShoppingCartDao

    companion object {
        @Volatile
        private var instance: ShoppingCartDatabase? = null
        private const val DATABASE_NAME = "shopping-cart-database"

        fun getDatabase(context: Context): ShoppingCartDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    ShoppingCartDatabase::class.java,
                    DATABASE_NAME,
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}
