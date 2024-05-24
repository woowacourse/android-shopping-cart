package woowacourse.shopping.data.product.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.product.dao.ProductDao
import woowacourse.shopping.data.product.entity.Product

@Database(entities = [Product::class], version = 1)
abstract class ProductDataBase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var instance: ProductDataBase? = null

        fun instance(context: Context): ProductDataBase {
            return instance ?: synchronized(this) {
                val newInstance =
                    Room.databaseBuilder(context, ProductDataBase::class.java, "products").build()
                instance = newInstance
                newInstance
            }
        }
    }
}
