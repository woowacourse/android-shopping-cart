package woowacourse.shopping.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        private const val DATABASE_NAME = "SHOPPING_DATABASE"
        private var instance: ShoppingDatabase? = null

        fun getInstance(context: Context): ShoppingDatabase =
            instance ?: Room
                .databaseBuilder(
                    context.applicationContext,
                    ShoppingDatabase::class.java,
                    DATABASE_NAME,
                ).build()
                .also {
                    instance = it
                }
    }
}
