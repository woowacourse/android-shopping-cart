package woowacourse.shopping.db.producthistory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductHistory::class], version = 1)
abstract class ProductHistoryDatabase : RoomDatabase() {
    abstract fun productHistoryDao(): ProductHistoryDao

    companion object {
        @Volatile
        private var instance: ProductHistoryDatabase? = null
        private const val PRODUCT_HISTORY_DATABASE = "productHistoryDatabase"

        fun getInstance(context: Context): ProductHistoryDatabase {
            var instance = instance

            if (instance == null) {
                instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        ProductHistoryDatabase::class.java,
                        PRODUCT_HISTORY_DATABASE,
                    ).build()
            }

            return instance
        }
    }
}
