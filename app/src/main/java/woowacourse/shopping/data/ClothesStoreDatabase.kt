package woowacourse.shopping.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.cart.CartDao
import woowacourse.shopping.data.cart.CartItemEntity
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.product.ProductEntity

@Database(entities = [ProductEntity::class, CartItemEntity::class], version = 1)
abstract class ClothesStoreDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao

    companion object {
        private const val DATABASE_NAME = "clothes-store-db"

        @Volatile
        private var INSTANCE: ClothesStoreDatabase? = null

        fun getInstance(context: Context): ClothesStoreDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ClothesStoreDatabase::class.java,
                    DATABASE_NAME,
                ).build().also { INSTANCE = it }
            }
        }
    }
}
