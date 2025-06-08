package woowacourse.shopping.data.product

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        private const val PRODUCT_DATABASE_NAME = "product_database"

        @Volatile
        private var database: ProductDatabase? = null

        fun database(context: Context): ProductDatabase {
            return database ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        ProductDatabase::class.java,
                        PRODUCT_DATABASE_NAME,
                    ).build()
                database = instance
                instance
            }
        }
    }
}
