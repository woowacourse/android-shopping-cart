package woowacourse.shopping.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.dao.PurchaseProductsDao
import woowacourse.shopping.data.entity.PurchaseProductEntity

@Database(
    entities = [PurchaseProductEntity::class],
    version = 1
)
abstract class DataBase: RoomDatabase() {
    abstract fun purchaseProductsDao(): PurchaseProductsDao

    companion object {
        @Volatile
        private var instance: DataBase? = null

        fun getDatabase(context: Context): DataBase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    klass = DataBase::class.java,
                    name = "purchase_products_database"
                ).build().also { instance = it }
            }
        }
    }
}