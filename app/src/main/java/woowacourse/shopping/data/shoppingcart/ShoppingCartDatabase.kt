package woowacourse.shopping.data.shoppingcart

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.product.ProductDao
import woowacourse.shopping.data.product.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        private const val PRODUCT_DATABASE_NAME = "shopping_cart_database"

        @Volatile
        private var database: ShoppingCartDatabase? = null

        fun database(context: Context): ShoppingCartDatabase {
            return database ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        ShoppingCartDatabase::class.java,
                        PRODUCT_DATABASE_NAME,
                    ).build()
                database = instance
                instance
            }
        }
    }
}
